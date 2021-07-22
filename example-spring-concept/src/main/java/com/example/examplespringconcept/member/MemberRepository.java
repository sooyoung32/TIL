package com.example.examplespringconcept.member;

public interface MemberRepository {
    void save(Member member);
    Member findById(Long memberId);
}
