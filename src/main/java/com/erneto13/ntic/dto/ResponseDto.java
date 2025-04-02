package com.erneto13.ntic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    private Object response;
    private String message;
}
