package com.mgmoura.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthPostRequestDTO {

	@NotBlank(message = "Por favor, informe o email de acesso do usuário.")
	@Email(message = "Por favor, informe um endereço de email válido.")
	private String email;

	@NotBlank(message = "Por favor, informe a senha de acesso do usuário.")
				@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{3,}$",	
				message = "Por favor, informe uma senha forte com no mínimo 3 caracteres.")
	private String senha;

}
