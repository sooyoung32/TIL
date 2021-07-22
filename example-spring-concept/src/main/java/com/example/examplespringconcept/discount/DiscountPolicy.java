package com.example.examplespringconcept.discount;

import com.example.examplespringconcept.member.Member;

public interface DiscountPolicy {

    /**
     *
     * @param member
     * @param price
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
