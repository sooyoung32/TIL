package com.example.examplespringconcept.order;

import com.example.examplespringconcept.discount.DiscountPolicy;
import com.example.examplespringconcept.discount.FixDiscountPolicy;
import com.example.examplespringconcept.discount.RateDiscountPolicy;
import com.example.examplespringconcept.member.Member;
import com.example.examplespringconcept.member.MemberRepository;
import com.example.examplespringconcept.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    /**
     *  할인정책 변경하려면 OrderServiceImpl 변경해야함 ㅠㅠ
     *  DIP 클래스 의존 관계 분석. 실제 코드 보면 추상 인터페이스(DiscountPolicy) 뿐 아니라 구현 클래스(RateDiscountPolicy)에도 의존하고 있다. -> DIP 위반 (항상 추상에 의존해라)
     *  FixDiscountPolicy 지우고 -> RateDiscountPolicy로 변경하는 순간 OrderServiceImpl 코드가 변경됨 -> OCP 위반 (변경하지 않고 확장)
     */
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    /**
     * 어떻게 해결할 수 있지?
     * DIP 위반 -> 인터페이스에만 의존
     * DIP 위반하지 않도록 인터페이스에만 의존하도록 변경!!
     *
     * 누군가 OrderServiceImpl에 discountPolicy의 구현을 대신해서 넣어줘야함!
      */
    private  DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
