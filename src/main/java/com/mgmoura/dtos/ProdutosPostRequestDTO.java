package com.mgmoura.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProdutosPostRequestDTO {
	
	@Pattern(regexp = "^[A-Za-zÀ-Üà-ü0-9\\s]{3,150}$", message = "Por favor, informe um nome válido de 3 a 150 caracteres.")
	@NotBlank(message = "Por favor, informe o nome do produto.")
	private String nome;

	@Pattern(regexp = "^[A-Za-zÀ-Üà-ü0-9\\s]{4,150}$", message = "Por favor, informe uma descrição válida de 4 a 150 caracteres.")
	@NotBlank(message = "Por favor, informe a descrição do produto.")
	private String descricao;

	
	@Min(value = 1, message = "O PREÇO deve ser maior ou igual a 1.")
	private Double preco;
	
	@Min(value = 1, message = "A QUANTIDADE deve ser maior ou igual a 1.")
	private Integer quantidade;
	

}
