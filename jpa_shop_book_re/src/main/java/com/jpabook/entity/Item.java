package com.jpabook.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.jpabook.constant.ItemSellStatus;
import com.jpabook.dto.ItemFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Item {
	
@Id
@Column(name = "item_id")
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column(nullable = false,length = 50)
private String itemNm;

@Column(nullable = false)
private int price;

@Column(nullable = false)
private int stockNumber;
@Lob
@Column(nullable = false)
private String itemDetail;

@Enumerated(EnumType.STRING)
private ItemSellStatus itemSellStatus;

private LocalDateTime regTime;

private LocalDateTime updateTime;

public void updateItem(ItemFormDto itemFormDto) {
	this.itemNm=itemFormDto.getItemNm();
	this.price=itemFormDto.getPrice();
	this.stockNumber=itemFormDto.getStockNumber();
	this.itemDetail=itemFormDto.getItemDetail();
	this.itemSellStatus=itemFormDto.getItemSellStatus();
}
}
