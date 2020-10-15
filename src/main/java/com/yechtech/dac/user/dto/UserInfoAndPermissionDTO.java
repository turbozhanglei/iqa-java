package com.yechtech.dac.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserInfoAndPermissionDTO {
    private String yumPSID;
    private String yumLocalName;
    private String adAccount;
    private String mail;
    private Set<String> permission;
    private String token;
    private String exipreTime;
    private Set<String> groups;
    private String showGroups;

}
