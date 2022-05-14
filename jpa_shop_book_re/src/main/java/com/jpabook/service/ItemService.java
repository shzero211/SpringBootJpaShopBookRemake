package com.jpabook.service;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jpabook.dto.ItemFormDto;
import com.jpabook.dto.ItemImgDto;
import com.jpabook.dto.ItemSearchDto;
import com.jpabook.entity.Item;
import com.jpabook.entity.ItemImg;
import com.jpabook.repository.ItemImgRepository;
import com.jpabook.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
private final ItemRepository itemRepository;
private final ItemImgService itemImgService;
private final ItemImgRepository itemImgRepository;

public Long saveItem(ItemFormDto itemFormDto,List<MultipartFile> itemImgFileList)throws Exception{
	Item item=itemFormDto.createItem();
	itemRepository.save(item);
	
	for(int i=0;i<itemImgFileList.size();i++) {
		ItemImg itemImg=new ItemImg();
		itemImg.setItem(item);
		if(i==0)
			itemImg.setRepimgYn("Y");
		else
			itemImg.setRepimgYn("N");
		itemImgService.saveItemImg(itemImg,itemImgFileList.get(i));
	}
	return item.getId();
}

@Transactional(readOnly = true)
public ItemFormDto getItemDtl(Long itemId) {
	List<ItemImg> itemImgList=itemImgRepository.findByItemIdOrderByIdAsc(itemId);
	List<ItemImgDto> itemImgDtoList=new ArrayList<>();
	for(ItemImg itemImg:itemImgList) {
		ItemImgDto itemImgDto=ItemImgDto.of(itemImg);
		itemImgDtoList.add(itemImgDto);
	}
	Item item=itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
	ItemFormDto itemFormDto=ItemFormDto.of(item);
	itemFormDto.setItemImgDtoList(itemImgDtoList);
	return itemFormDto;
}

public Long updateItem(ItemFormDto itemFormDto,List<MultipartFile> itemImgFileList) throws Exception {
	Item item=itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
	item.updateItem(itemFormDto);
	
	List<Long> itemImgIds=itemFormDto.getItemImgIds();
	for(int i=0;i<itemImgFileList.size();i++) {
		itemImgService.updateItemImg(itemImgIds.get(i),itemImgFileList.get(i));
	}
	return item.getId();
}

public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto,Pageable pageable){
	return itemRepository.getAdminItemPage(itemSearchDto, pageable);
}
}
