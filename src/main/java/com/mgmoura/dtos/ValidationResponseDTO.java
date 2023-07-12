package com.mgmoura.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ValidationResponseDTO {
	
	private HttpStatus status;
	private String message;
	private List<ValidationErrorResponseDTO> errors;

}
