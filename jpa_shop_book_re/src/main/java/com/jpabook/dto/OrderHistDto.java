package com.jpabook.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.jpabook.constant.OrderStatus;
import com.jpabook.entity.Order;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderHistDto {
private Long orderId;
private String orderDate;
private OrderStatus orderStatus;
private List<OrderItemDto> orderItemDtoList=new ArrayList<>();
public void addOrderItemDto(OrderItemDto orderItemDto) {
	orderItemDtoList.add(orderItemDto);
}
public OrderHistDto(Order order) {
	this.orderId=order.getId();
	this.orderDate=order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	this.orderStatus=order.getOrderStatus();
}

}
