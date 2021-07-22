package com.example.examplespringconcept.order;

import com.example.examplespringconcept.discount.DiscountPolicy;
import com.example.examplespringconcept.discount.FixDiscountPolicy;
import com.example.examplespringconcept.member.Member;
import com.example.examplespringconcept.member.MemberRepository;
import com.example.examplespringconcept.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
