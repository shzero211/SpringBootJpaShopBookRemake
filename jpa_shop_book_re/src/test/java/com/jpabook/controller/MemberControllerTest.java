package com.jpabook.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.dto.MemberFormDto;
import com.jpabook.entity.Member;
import com.jpabook.service.MemberService;
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MemberService memberService;
	public Member createMember(String email,String password) {
	MemberFormDto memberFormDto=new MemberFormDto();
	memberFormDto.setEmail(email);
	memberFormDto.setPassword(password);
	memberFormDto.setName("김상훈");
	memberFormDto.setAddress("서울시");
	Member member=Member.createMember(memberFormDto,passwordEncoder);
	
	return memberService.saveMember(member);
}
	
	
@Test
@DisplayName("로그인테스트")
public void login() throws Exception {
	Member member=this.createMember("test@test.com","1234");
	mockMvc.perform(formLogin().userParameter("email").loginProcessingUrl("/members/login").user("test@test.com").password("1234")).andExpect(SecurityMockMvcResultMatchers.authenticated());
}

}
