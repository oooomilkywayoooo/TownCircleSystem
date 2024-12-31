package com.example.app.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Questionnaire {
	private Integer id;
	@NotBlank
	private String name;
	@NotBlank
	private String url;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;
	private Integer deleteFlg;
	private Date created;
}
