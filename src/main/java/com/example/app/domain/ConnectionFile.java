package com.example.app.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConnectionFile {
	private Integer id;
	private String name;
	@NotBlank
	private String note;
	private String filePath;
	private Integer deleteFlg;
	private Date created;
	// 画像のアップロード
	private MultipartFile upfile;
}
