package com.example.examplespringconcept.order;

import com.example.examplespringconcept.member.Grade;
import com.example.examplespringconcept.member.Member;
import com.example.examplespringconcept.member.MemberService;
import com.example.examplespringconcept.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        //given
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        //when
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        //then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}