package com.example.examplespringconcept;

import com.example.examplespringconcept.member.Grade;
import com.example.examplespringconcept.member.Member;
import com.example.examplespringconcept.member.MemberService;
import com.example.examplespringconcept.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl(); //DIP 위반
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = "+member.getName());
        System.out.println("find member = "+findMember.getName());
    }
}
