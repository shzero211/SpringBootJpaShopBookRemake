package com.jpabook.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

//파일을 업로드하는 메소드와 삭제하는메소드
@Service
@Log
public class FileService {
public String uploadFile(String uploadPath,String originalFileName,byte[] fileData) throws Exception {
	UUID uuid=UUID.randomUUID();
	String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
	String savedFileName=uuid.toString()+extension;
	String fileUploadFullUrl=uploadPath+"/"+savedFileName;
	FileOutputStream fos=new FileOutputStream(fileUploadFullUrl);
	fos.write(fileData);
	fos.close();
	return savedFileName;
	
}
public void deleteFile(String filePath)throws Exception{
	File deleteFile=new File(filePath);
	if(deleteFile.exists()) {
		deleteFile.delete();
		log.info("파일을 삭제 하였습니다.");
	}else {
		log.info("파일이 존재하지 않습니다.");
	}
}
}
