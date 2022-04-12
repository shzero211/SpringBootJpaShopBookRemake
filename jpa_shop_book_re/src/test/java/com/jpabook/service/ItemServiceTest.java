package com.jpabook.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import com.jpabook.constant.ItemSellStatus;
import com.jpabook.dto.ItemFormDto;
import com.jpabook.entity.Item;
import com.jpabook.entity.ItemImg;
import com.jpabook.repository.ItemImgRepository;
import com.jpabook.repository.ItemRepository;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemServiceTest {
@Autowired
ItemService itemService;

@Autowired
ItemRepository itemRepository;

@Autowired
ItemImgRepository itemImgRepository;

List<MultipartFile> createMultipartFiles() throws Exception{
	List<MultipartFile> multipartFileList=new ArrayList<>();
	for(int i=0;i<5;i++) {
		String path="C:/shop/item/";
		String imageName="image"+i+".jpg";
		MockMultipartFile multipartFile=new MockMultipartFile(path,imageName,"image/jpg",new byte[] {1,2,3,4});
	multipartFileList.add(multipartFile);
	}
	return multipartFileList;
}

@Test
@DisplayName("상품 등록 테스트")
@WithMockUser(username = "admin",roles = "ADMIN")
public void saveItem()throws Exception{
	ItemFormDto itemFormDto=new ItemFormDto();
	itemFormDto.setItemNm("테스트상품");
	itemFormDto.setItemDetail("테스트 상품 입니다");
	itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
	itemFormDto.setPrice(1000);
	itemFormDto.setStockNumber(100);
	
	List<MultipartFile> multipartFIleList=this.createMultipartFiles();
	Long itemId=itemService.saveItem(itemFormDto, multipartFIleList);
	
	List<ItemImg> itemImgList=itemImgRepository.findByItemIdOrderByIdAsc(itemId);
	Item item=itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
	
	assertEquals(itemFormDto.getItemNm(),item.getItemNm());
	assertEquals(multipartFIleList.get(0).getOriginalFilename(),itemImgList.get(0).getOriImgName());
}
}
