package com.jefferpgdev.ecommerceprices.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {

    private Integer status;
    private String type;
    private String message;

}
