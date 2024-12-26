package com.example.app.domain;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CircularBoard {
	private Integer id;
	@NotBlank
	private String name;
	@NotBlank
	private String filePath;
	private Integer deleteFlg;
	private Date created;
}
