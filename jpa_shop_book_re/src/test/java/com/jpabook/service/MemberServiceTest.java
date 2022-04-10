package com.jpabook.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.dto.MemberFormDto;
import com.jpabook.entity.Member;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {
@Autowired
private MemberService memberService;

@Autowired
private PasswordEncoder passwordEncoder;

public Member createMember() {
	MemberFormDto memberFormDto=new MemberFormDto();
	memberFormDto.setEmail("test@email.com");
	memberFormDto.setName("홍길동");
	memberFormDto.setAddress("서울시 마포구 합정동");
	memberFormDto.setPassword("1234");
	return Member.createMember(memberFormDto, passwordEncoder);
}

@Test
@DisplayName("회원가입")
public void saveMemberTest() {
	Member member=this.createMember();
	Member savedMember=memberService.saveMember(member);
	assertEquals(member.getEmail(),savedMember.getEmail());
}

@Test
@DisplayName("회원가입")
public void saveDuplicateMemberTest() {
	Member member1=this.createMember();
	Member member2=this.createMember();
	memberService.saveMember(member1);
	
	Throwable e=assertThrows(IllegalStateException.class,()->{memberService.saveMember(member2);});
	assertEquals("이미 가입된 회원입니다.",e.getMessage());
}

}
