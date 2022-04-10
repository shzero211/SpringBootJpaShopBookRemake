package com.jpabook.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import jdk.jfr.Label;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
@NotBlank(message = "이름은 필수 입력 값입니다.")
private String name;

@Email(message = "이메일 형식으로 입력해주세요.")
@NotEmpty(message = "이메일은 필수 입력 값입니다.")
private String email;

@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
@Length(min = 4,max = 8,message = "비밀번호는 4~6자 이하로 입력 해주세요.")
private String password;

@NotEmpty(message = "주소는 필수 입력 값입니다.")
private String address;
}
