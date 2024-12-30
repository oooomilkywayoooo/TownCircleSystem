package com.example.app.domain;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatMessage {
	private Integer id;
	@NotBlank
	private String message;
	private Member member;
	private Integer deleteFlg;
	private Date created;
}
