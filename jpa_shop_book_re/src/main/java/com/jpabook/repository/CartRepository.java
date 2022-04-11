package com.jpabook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpabook.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>  {

}
