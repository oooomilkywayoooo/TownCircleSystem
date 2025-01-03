package com.example.app.domain;

import com.example.app.validation.LoginMember;
import com.example.app.validation.RegisterMember;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberForm {
	@NotBlank(groups = {RegisterMember.class})
    private String lastName;
    @NotBlank(groups = {RegisterMember.class})
    private String firstName;
    @NotBlank(groups = {RegisterMember.class})
    private String lastNameKana;
    @NotBlank(groups = {RegisterMember.class})
    private String firstNameKana;
    @NotBlank(groups = {RegisterMember.class})
    private String address;
    @NotBlank(groups = {RegisterMember.class})
    @Size(max = 11)
    private String tel;
    @NotBlank(groups = {RegisterMember.class, LoginMember.class})
    @Email(groups = {RegisterMember.class})
    private String email;
    @NotNull(groups = {RegisterMember.class})
    private Integer groupId;
    @NotNull(groups = {RegisterMember.class})
    private Integer familyNumber;
    @NotBlank(groups = {RegisterMember.class, LoginMember.class})
    private String password;
    @NotBlank(groups = {RegisterMember.class})
    private String passwordConf;
}
