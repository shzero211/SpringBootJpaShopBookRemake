package com.jpabook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpabook.entity.ItemImg;

public interface ItemImgRepository  extends JpaRepository<ItemImg, Long>{
List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
}
