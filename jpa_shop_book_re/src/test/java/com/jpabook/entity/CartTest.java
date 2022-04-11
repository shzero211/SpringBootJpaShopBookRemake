package com.jpabook.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.dto.MemberFormDto;
import com.jpabook.repository.CartRepository;
import com.jpabook.repository.MemberRepository;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	EntityManager em;
	public Member createMember() {
		MemberFormDto memberFormDto=new MemberFormDto();
		memberFormDto.setEmail("test@test.com");
		memberFormDto.setName("홍길동");
		memberFormDto.setAddress("서울시");
		memberFormDto.setPassword("1234");
		return Member.createMember(memberFormDto,passwordEncoder);
	}
	
	@Test
	@DisplayName("장바구니 회원 엔티티 매핑조회 테스트")
	public void findCartAndMemberTest() {
		Member member=this.createMember();
		memberRepository.save(member);
		Cart cart=new Cart();
		cart.setMember(member);
		cartRepository.save(cart);
		em.flush();
		em.clear();
		Cart savedCart=cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
		assertEquals(savedCart.getMember().getId(),member.getId());
		
		
	}

}
