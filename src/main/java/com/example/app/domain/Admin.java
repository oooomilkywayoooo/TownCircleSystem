package com.example.app.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Admin {
	private Integer id;
	@NotBlank
	private String loginId;
	@NotBlank
	private String loginPass;
	@Email
	private String email;
	private String tel;
}
