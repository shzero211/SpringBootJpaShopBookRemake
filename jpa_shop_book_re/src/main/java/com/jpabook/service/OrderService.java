package com.jpabook.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jpabook.dto.OrderDto;
import com.jpabook.dto.OrderHistDto;
import com.jpabook.dto.OrderItemDto;
import com.jpabook.entity.Item;
import com.jpabook.entity.ItemImg;
import com.jpabook.entity.Member;
import com.jpabook.entity.Order;
import com.jpabook.entity.OrderItem;
import com.jpabook.repository.ItemImgRepository;
import com.jpabook.repository.ItemRepository;
import com.jpabook.repository.MemberRepository;
import com.jpabook.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
private final ItemRepository itemRepository;
private final ItemImgRepository itemImgRepository;
private final MemberRepository memberRepository;
private final OrderRepository orderRepository;

public Long order(OrderDto orderDto,String email) {
	Item item=itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
	Member member=memberRepository.findByEmail(email);
	
	List<OrderItem> orderItemList=new ArrayList<>();
	OrderItem orderItem=OrderItem.createOrderItem(item, orderDto.getCount());
	orderItemList.add(orderItem);
	
	Order order=Order.createOrder(member, orderItemList);
	orderRepository.save(order);
	
	return order.getId();
}
public Page<OrderHistDto> getOrderList(String email,Pageable pageable){
	List<Order> orders=orderRepository.findOrders(email, pageable);
	Long totalCount=orderRepository.countOrder(email);
	List<OrderHistDto> orderHistDtos=new ArrayList<>();
	for(Order order :orders) {
		OrderHistDto orderHistDto=new OrderHistDto(order);
		List<OrderItem> orderItems=order.getOrderItems();
		for(OrderItem orderItem:orderItems) {
			ItemImg itemImg=itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(),"Y");
			OrderItemDto orderItemDto=new OrderItemDto(orderItem,itemImg.getImgUrl());
			orderHistDto.addOrderItemDto(orderItemDto);
		}
		orderHistDtos.add(orderHistDto);
	}
	return new PageImpl<>(orderHistDtos,pageable,totalCount);
}
}
