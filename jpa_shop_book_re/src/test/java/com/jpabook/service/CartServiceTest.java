package com.jpabook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.repository.MemberRepository;
import com.jpabook.constant.ItemSellStatus;
import com.jpabook.dto.CartItemDto;
import com.jpabook.entity.CartItem;
import com.jpabook.entity.Item;
import com.jpabook.entity.Member;
import com.jpabook.repository.CartItemRepository;
import com.jpabook.repository.ItemRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartServiceTest {
@Autowired
ItemRepository itemRepository;
@Autowired
MemberRepository memberRepository;
@Autowired
CartService cartService;
@Autowired
CartItemRepository cartItemRepository;
public Item saveItem() {
	Item item=new Item();
	item.setItemNm("테스트 상품");
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
@DisplayName("장바구니 담기 테스트")
public void addCart() {
	Item item =saveItem();
	Member member=saveMember();
	
	CartItemDto cartItemDto=new CartItemDto();
	cartItemDto.setItemId(item.getId());
	cartItemDto.setCount(5);
	
	Long cartItemId=cartService.addCart(cartItemDto,member.getEmail());
	
	CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
	assertEquals(cartItem.getItem().getId(),item.getId());
}
}
