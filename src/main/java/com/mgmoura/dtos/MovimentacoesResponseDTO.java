package com.mgmoura.dtos;

import org.springframework.http.HttpStatus;

import com.mgmoura.entities.Movimentacao;

import lombok.Data;

@Data
public class MovimentacoesResponseDTO {
	
	private HttpStatus status;
	private String mensagem;
	private Movimentacao movimentacao; 

}
