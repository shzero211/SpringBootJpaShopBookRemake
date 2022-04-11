package com.jpabook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Cart {
@Id
@Column(name = "cart_id")
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "member_id")
private Member member;


}
