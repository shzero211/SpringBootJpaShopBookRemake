package com.jpabook.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jpabook.dto.OrderDto;
import com.jpabook.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
private final OrderService orderService;

@PostMapping(value = "/order")
public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto,BindingResult bindingResult,Principal principal) {
	if(bindingResult.hasErrors()) {
		StringBuilder sb=new StringBuilder();
		List<FieldError> fieldErros=bindingResult.getFieldErrors();
		for(FieldError fieldError:fieldErros) {
			sb.append(fieldError.getDefaultMessage());
		}
		System.out.println("firsterror");
		return new ResponseEntity<String>(sb.toString(),HttpStatus.BAD_REQUEST);
	}
	String email=principal.getName();
	Long orderId;
	try {
		orderId=orderService.order(orderDto, email);
	}catch (Exception e) {
		System.out.println("seconderror");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<Long>(orderId,HttpStatus.OK);
}
}
