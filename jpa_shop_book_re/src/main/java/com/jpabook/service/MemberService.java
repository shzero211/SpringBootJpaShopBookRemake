package com.jpabook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.entity.Member;
import com.jpabook.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
private final MemberRepository memberRepository;

public Member saveMember(Member member) {
	validateDuplicateMember(member);
	return memberRepository.save(member);
}

private void validateDuplicateMember(Member member) {
	Member findMember=memberRepository.findByEmail(member.getEmail());
	if(findMember!=null)
		throw new IllegalStateException("이미 가입된 회원입니다.");
	
}
}
