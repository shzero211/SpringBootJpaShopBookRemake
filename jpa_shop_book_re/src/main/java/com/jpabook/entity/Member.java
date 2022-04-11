package com.jpabook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.jpabook.constant.Role;
import com.jpabook.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Member {
@Id
@Column(name = "member_id")
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

private String name;

@Column(unique = true)
private String email;

private String password;

private String address;

@Enumerated(EnumType.STRING)
private Role role;

public static Member createMember(MemberFormDto memberFormDto,PasswordEncoder passwordEncoder) {
	Member member=new Member();
	member.setName(memberFormDto.getName());
	member.setEmail(memberFormDto.getEmail());
	String password=passwordEncoder.encode(memberFormDto.getPassword());
	member.setPassword(password);
	member.setAddress(memberFormDto.getAddress());
	member.setRole(Role.ADMIN);
	return member;
}
}
