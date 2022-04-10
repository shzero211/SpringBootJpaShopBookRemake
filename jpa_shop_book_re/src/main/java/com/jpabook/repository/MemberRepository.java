package com.jpabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpabook.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
Member findByEmail(String email);
}
