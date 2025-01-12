package com.example.app.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Schedule {
	private Integer id;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date eventAt;
	private Integer deleteFlg;
	private Date created;
	private Date updated;
}
