package com.jpabook.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jpabook.dto.ItemFormDto;
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
}
