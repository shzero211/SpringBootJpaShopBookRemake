package com.jpabook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpabook.entity.Item;

public interface ItemRepository  extends JpaRepository<Item,Long>{
List<Item> findByItemNm(String ItemNm);
List<Item> findByItemNmOrItemDetail(String itemNm,String itemDetail);
List<Item> findByPriceLessThan(Integer price);
List<Item>findByPriceLessThanOrderByPriceDesc(Integer price);
@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
List<Item> findByItemDetail(@Param("itemDetail")String itemDetail);
}