package com.jpabook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jpabook.dto.ItemSearchDto;
import com.jpabook.entity.Item;

public interface ItemRepositoryCustom {
Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto,Pageable pageable);
}
