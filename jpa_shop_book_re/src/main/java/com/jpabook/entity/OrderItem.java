package com.jpabook.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "item_id")
private Item item;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "order_id")
private Order order;

private int orderPrice;
private int count;

}
