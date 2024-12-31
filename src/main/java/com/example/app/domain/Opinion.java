package com.example.app.domain;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Opinion {
	private Integer id;
	@NotBlank
	private String content;
	private Member member;
	private Date created;
}
