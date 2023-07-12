package com.mgmoura.dtos;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class AuthResponseDTO {
	
	private HttpStatus status;
	private String mensagem;
	private String accessToken;
	private String nomeUsuario;
	private String emailUsuario;

}
