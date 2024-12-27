package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Group {
	private Integer id;
	@NotBlank
	private String name;
	private Integer deleteFlg;
}
