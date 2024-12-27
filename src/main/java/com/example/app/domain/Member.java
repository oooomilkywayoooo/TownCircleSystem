package com.example.app.domain;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Member {
	private Integer id;
	@NotBlank
	private String name;
	@NotBlank
	private String nameKana;
	@NotBlank
	private String address;
	@NotBlank
	@Size(max=11)
	private String tel;
	@NotBlank
	private String email;
	@NotBlank
	private Integer familyNumber;
	@NotBlank
	private String password;
	private Integer deleteFlg;
	private Date created;
	private Date updated;
	private Group group;
}
