package com.jpabook.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.dto.CartItemDto;
import com.jpabook.entity.Cart;
import com.jpabook.entity.CartItem;
import com.jpabook.entity.Item;
import com.jpabook.entity.Member;
import com.jpabook.repository.CartItemRepository;
import com.jpabook.repository.CartRepository;
import com.jpabook.repository.ItemRepository;
import com.jpabook.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
public Long addCart(CartItemDto cartItemDto,String email) {
	Item item=itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
	Member member=memberRepository.findByEmail(email);
	Cart cart=cartRepository.findByMemberId(member.getId());
	if(cart==null) {
		cart=Cart.createCart(member);
		cartRepository.save(cart);
	}
	CartItem savedCartItem=cartItemRepository.findByCartIdAndItemId(cart.getId(),item.getId());
	if(savedCartItem==null) {
		savedCartItem=CartItem.createCartItem(cart, item,cartItemDto.getCount());
		cartItemRepository.save(savedCartItem);
		return savedCartItem.getId();
	}else {
		savedCartItem.addCount(cartItemDto.getCount());
		return savedCartItem.getId();
	}
}
}
