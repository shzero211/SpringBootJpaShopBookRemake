package com.jpabook.controller;

import javax.validation.Valid;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpabook.dto.MemberFormDto;
import com.jpabook.entity.Member;
import com.jpabook.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
private final MemberService memberService;
private final PasswordEncoder passwordEncoder;
@GetMapping("/new")
public String memberForm(Model model) {
	model.addAttribute("memberFormDto",new MemberFormDto());
	return "member/memberForm";
}

@PostMapping(value = "/new")
public String memberForm(@Valid MemberFormDto memberFormDto,BindingResult bindingResult,Model model) {
	if(bindingResult.hasErrors()) {
		return "member/memberForm";
	}
	
	try {
	Member member=Member.createMember(memberFormDto,passwordEncoder);
	memberService.saveMember(member);
	}catch (IllegalStateException e) {
		model.addAttribute("errorMessage",e.getMessage());
		return "member/memberForm";
	}
	return "redirect:/";
}
}