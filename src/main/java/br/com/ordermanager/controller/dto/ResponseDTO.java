package br.com.ordermanager.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ResponseDTO {
    private Integer status;
    private String message;
    private Object body;
}