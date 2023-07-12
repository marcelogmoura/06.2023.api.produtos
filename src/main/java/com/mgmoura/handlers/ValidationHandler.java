package com.mgmoura.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mgmoura.dtos.ValidationErrorResponseDTO;
import com.mgmoura.dtos.ValidationResponseDTO;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ValidationErrorResponseDTO> errors = new ArrayList<ValidationErrorResponseDTO>();
		
		for(FieldError error : ex.getBindingResult().getFieldErrors()) {
			ValidationErrorResponseDTO dto = new ValidationErrorResponseDTO();
			dto.setName(error.getField());
			dto.setError(error.getDefaultMessage());
			errors.add(dto);
		}
		
		for(ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			ValidationErrorResponseDTO dto = new ValidationErrorResponseDTO();
			dto.setName(error.getObjectName());
			dto.setError(error.getDefaultMessage());
			errors.add(dto);
		}
		
		ValidationResponseDTO response = new ValidationResponseDTO();
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setMessage("Ocorreram erros de validação no preenchimento dos dados, por favor verifique.");
		response.setErrors(errors);
		
		return handleExceptionInternal(ex, response, headers, response.getStatus(), request);
	}
}
