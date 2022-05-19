package com.jpabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpabook.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
CartItem findByCartIdAndItemId(Long cartId,Long itemId);
}
