package com.dh.apiClinic.security.controller;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiResponse <T>  {
    private String message;
    private T model;

    public ApiResponse(String message, T model) {
        this.message = message;
        this.model = model;
    }
}
