package com.jpabook.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MainItemDto {
private Long id;
private String itemNm;
private String itemDetail;
private int price;
//imgUrl까지 전송
private String imgUrl;

@QueryProjection
public MainItemDto(Long id,String itemNm,String imgUrl,String itemDetail,int price) {
	this.id=id;
	this.itemNm=itemNm;
	this.imgUrl=imgUrl;
	this.price=price;
	this.itemDetail=itemDetail;
}
}
