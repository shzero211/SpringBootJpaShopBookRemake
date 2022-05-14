package com.jpabook.repository;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.constant.ItemSellStatus;
import com.jpabook.entity.Item;
import com.jpabook.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {
	/**
	@Autowired
	private ItemRepository itemRepository;
	@PersistenceContext
	EntityManager em;
	
@Test
public void createItemTest() {
	Item item=new Item();
	item.setItemNm("테스트 상품");
	item.setPrice(10000);
	item.setItemSellStatus(ItemSellStatus.SELL);
	item.setStockNumber(100);
	item.setRegtime(LocalDateTime.now());
	item.setUpdateTime(LocalDateTime.now());
	item.setItemDetail("테스트 상품 상세");
	Item savedItem=itemRepository.save(item);
	System.out.println(savedItem);
}

public void createItemList() {
	for(int i=0;i<=10;i++) {
		Item item=new Item();
		item.setItemNm("테스트 상품"+i);
		item.setPrice(10000+i);
		item.setItemDetail("테스트 상품 상세"+i);
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegtime(LocalDateTime.now());
		item.setUpdateTime(LocalDateTime.now());
		Item savedItem=itemRepository.save(item);
	}
}

@Test
@DisplayName("상품명 조회 테스트")
public void findByItemNmTest() {
	this.createItemList();
	List<Item> itemList=itemRepository.findByItemNm("테스트 상품1");
	for(Item item:itemList) {
		System.out.println(item.toString());
	}
}

@Test
@DisplayName("상품명,상품상세설명or테스트")
public void findByItemNmOrItemDetailTest() {
	this.createItemList();
	List<Item> itemList=itemRepository.findByItemNmOrItemDetail("테스트 상품1","테스트 상품 상세2");
	for(Item item:itemList) {
		System.out.println(item.toString());
	}
}

@Test
@DisplayName("가격 LessThan 테스트")
public void findByPriceLessThan() {
	this.createItemList();
	List<Item> itemList=itemRepository.findByPriceLessThan(10005);
	for(Item item:itemList) {
		System.out.println(item.toString());
	}
}
@Test
@DisplayName("가격 내림차순 조회테스트")
public void findByPriceLessThanOrderByPriceDesce() {
	this.createItemList();
	List<Item> itemList=itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
	for(Item item:itemList) {
		System.out.println(item.toString());
	}
}
@Test
@DisplayName("@Query를 이용한 상품 조회")
public void findByItemDetailTest() {
	this.createItemList();
	List<Item> itemList=itemRepository.findByItemDetail("테스트 상품 상세");
	for(Item item:itemList) {
		System.out.println(item.toString());
	}
}

@Test
@DisplayName("QueryDsl 조회 테스트")
public void queryDslTest() {
	this.createItemList();
	JPAQueryFactory queryFactory=new JPAQueryFactory(em);
	QItem qItem=QItem.item;
	JPAQuery<Item> query=queryFactory.selectFrom(qItem)
			.where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
			.where(qItem.itemDetail.like("%"+"테스트 상품 상세"+"%"))
			.orderBy(qItem.price.desc());
	
	List<Item> itemList=query.fetch();
	for(Item item:itemList) {
		System.out.println(item.toString());
	}
}**/
}
