package com.jpabook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.jpabook.constant.ItemSellStatus;
import com.jpabook.dto.OrderDto;
import com.jpabook.entity.Item;
import com.jpabook.entity.Member;
import com.jpabook.entity.Order;
import com.jpabook.repository.ItemRepository;
import com.jpabook.repository.MemberRepository;
import com.jpabook.repository.OrderRepository;

import groovy.util.logging.Log4j;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderServiceTest {
@Autowired
private OrderService orderService;
@Autowired
private OrderRepository orderRepository;
@Autowired
ItemRepository itemRepository;
@Autowired
MemberRepository memberRepository;
public Item saveItem() {
	Item item=new Item();
	item.setItemNm("테스트상품");
	item.setPrice(10000);
	item.setItemDetail("테스트 상품 상세 설명");
	item.setItemSellStatus(ItemSellStatus.SELL);
	item.setStockNumber(100);
	return itemRepository.save(item);
}
public Member saveMember() {
	Member member=new Member();
	member.setEmail("test@test.com");
	return memberRepository.save(member);
}
@Test
@DisplayName("주문 테스트")
public void order() {
	Item item=saveItem();
	Member member=saveMember();
	
	OrderDto orderDto=new OrderDto();
	orderDto.setItemId(item.getId());
	orderDto.setCount(10);
	Long orderId=orderService.order(orderDto,member.getEmail());
	Order order=orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
	assertEquals(order.getTotalPrice(),item.getPrice()*orderDto.getCount());
	
}
}
