package com.jpabook.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jpabook.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
@Id
@GeneratedValue(strategy =GenerationType.AUTO)
@Column(name = "order_id")
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "member_id")
private Member member;

@OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
private List<OrderItem> orderItems=new ArrayList<>();


private LocalDateTime orderDate;

@Enumerated(EnumType.STRING)
private OrderStatus orderStatus;

private LocalDateTime regTime;

private LocalDateTime updateTime;
}
