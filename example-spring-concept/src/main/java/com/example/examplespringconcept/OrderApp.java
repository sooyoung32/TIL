package com.example.examplespringconcept;

import com.example.examplespringconcept.member.Grade;
import com.example.examplespringconcept.member.Member;
import com.example.examplespringconcept.member.MemberService;
import com.example.examplespringconcept.member.MemberServiceImpl;
import com.example.examplespringconcept.order.Order;
import com.example.examplespringconcept.order.OrderService;
import com.example.examplespringconcept.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl(); //DIP, OCP 위반  "계획에 따르기보단 변화에 대응하기를!"
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = "+ order);

    }
}
