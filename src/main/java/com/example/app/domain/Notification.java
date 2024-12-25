package com.example.app.domain;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Notification {
	private Integer id;
	@NotBlank
	private String content;
	private Integer deleteFlg;
	private Date created;
	private Date updated;
}
