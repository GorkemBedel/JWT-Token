package com.SpringSecurity.jwttoken.dto;

public record AuthRequest(
        String username,
        String password
) {

}
