package com.mgmoura.dtos;

import org.springframework.http.HttpStatus;

import com.mgmoura.entities.Produto;

import lombok.Data;

@Data
public class ProdutosResponseDTO {
	
	private HttpStatus status;
	private String mensagem;
	private Produto produto;

}
