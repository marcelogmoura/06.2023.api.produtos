package com.mgmoura.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MovimentacoesPostRequestDTO {
	
	@Min(value = 1 , message = "ID do produto deve ser maior ou igual a 1.")
	private Integer idProduto;
	
	@Min(value = 1, message = "Tipo da movimentação deve ser [1] Entrada ou [2] Saída.")
	@Max(value = 2, message = "Tipo da movimentação deve ser [1] Entrada ou [2] Saída.")
	private Integer tipo;
	
	@Pattern(regexp = "^[A-Za-zÀ-Üà-ü0-9\\s]{3,500}$", message = "Por favor, informe uma observação válida de 3 a 500 caracteres.")
	@NotBlank(message = "Por favor, informe	as OBSERVAÇÕES da movimentação.")
	private String observacoes;
	
	@Min(value = 1, message = "Quantidade da movimentação deve ser maior ou igual a 1.")
	private Integer quantidade;
	
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Informe uma data no formato 'yyyy-MM-dd'.")
	@NotBlank(message = "Por favor, informe a DATA DA MOVIMENTAÇÃO.") 
	private String dataMovimentacao;

}
