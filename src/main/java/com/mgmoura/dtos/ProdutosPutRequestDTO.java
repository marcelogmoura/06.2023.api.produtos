package com.mgmoura.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProdutosPutRequestDTO {
	
	@Min(value = 1, message = "Id do produto deve ser maior	ou igual a 1.")
	private Integer idProduto;
	
	@Pattern(regexp = "^[A-Za-zÀ-Üà-ü0-9\\s]{3,150}$",
			message = "Por favor, informe um nome válido de 3 a 150 caracteres.")
			@NotBlank(message = "Por favor, informe o NOME do produto.")
	private String nome;
	
	@Pattern(regexp = "^[A-Za-zÀ-Üà-ü0-9\\s]{3,150}$",
			message = "Por favor, informe um nome válido de 3 a 150 caracteres.")
			@NotBlank(message = "Por favor, informe a DESCRIÇÃO do produto.")
	private String descricao;
	
	@Min(value = 1, message = "O PREÇO deve ser maior ou igual a 1.")
	private Double preco;
	
	@Min(value = 1, message = "O QUANTIDADE deve ser maior ou igual a 1.")
	private Integer quantidade;

}


