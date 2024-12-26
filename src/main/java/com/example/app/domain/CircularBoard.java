package com.example.app.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CircularBoard {
	private Integer id;
	private String name;
	private String filePath;
	private Integer deleteFlg;
	private Date created;
	// 画像のアップロード
	private MultipartFile upfile;
}
