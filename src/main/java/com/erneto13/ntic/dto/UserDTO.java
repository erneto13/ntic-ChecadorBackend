package com.erneto13.ntic.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private RoleDTO role;
}
