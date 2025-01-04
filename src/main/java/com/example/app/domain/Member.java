package com.example.app.domain;

import java.util.Date;

import com.example.app.validation.EditMember;
import com.example.app.validation.EditPassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Member {
	private Integer id;
	@NotBlank(groups = {EditMember.class})
	private String name;
	@NotBlank(groups = {EditMember.class})
	private String nameKana;
	@NotBlank(groups = {EditMember.class})
	private String address;
	@NotBlank(groups = {EditMember.class})
	@Size(max=11)
	private String tel;
	private String email;
	@NotNull(groups = {EditMember.class})
	private Integer familyNumber;
	@NotBlank(groups = {EditPassword.class})
	private String password;
	@NotBlank(groups = {EditPassword.class})
	private String passwordConf;
	private Integer deleteFlg;
	private Date created;
	private Date updated;
	private Group group;
}
