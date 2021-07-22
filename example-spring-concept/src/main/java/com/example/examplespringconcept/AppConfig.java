package com.example.examplespringconcept;

import com.example.examplespringconcept.discount.FixDiscountPolicy;
import com.example.examplespringconcept.member.MemberService;
import com.example.examplespringconcept.member.MemberServiceImpl;
import com.example.examplespringconcept.member.MemoryMemberRepository;
import com.example.examplespringconcept.order.OrderService;
import com.example.examplespringconcept.order.OrderServiceImpl;

/**
 * 어플리케이션 전체 동작 방식을 구성(Config)하기 위해, 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 클래스
 *
 * AppConfig는 어플리케이션의 실제 동작에 필요한 구현객체 생성
 * AppConfig는 생성한 객체 인스턴스(참조레퍼런스)를 생성자로 주입(연결)
 *
 * MemberRepositoryImpl은 의존관계에 대한 고민은 외부에서 결정!!하고 실행(역할)!!에만 집중한다.
 *
 * 객체의 생성과 연결은 AppConfig가 담당한다
 * DIP 완성 !! 모두 인터페이스에 의존
 * 관심의 분리 : 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리 되었다.
 */
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }


}
