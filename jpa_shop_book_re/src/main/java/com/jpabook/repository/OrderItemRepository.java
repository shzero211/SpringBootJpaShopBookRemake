package com.jpabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpabook.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
