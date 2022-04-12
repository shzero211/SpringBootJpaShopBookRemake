package com.jpabook.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.jpabook.entity.ItemImg;
import com.jpabook.repository.ItemImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
	
@Value("${itemImgLocation}")
private String itemImgLocation;

private final ItemImgRepository itemImgRepository;

private final FileService fileService;

public void saveItemImg(ItemImg itemImg,MultipartFile itemImgFile) throws Exception {
	String oriImgName=itemImgFile.getOriginalFilename();
	String imgName="";
	String imgUrl="";
	if(!StringUtils.isEmpty(oriImgName)) {
		imgName=fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
		imgUrl="/images/item/"+imgName;
	}
	itemImg.updateItemImg(oriImgName, imgName, imgUrl);
	itemImgRepository.save(itemImg);
}
}
