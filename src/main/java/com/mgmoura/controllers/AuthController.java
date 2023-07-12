package com.mgmoura.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgmoura.dtos.AuthPostRequestDTO;
import com.mgmoura.dtos.AuthResponseDTO;
import com.mgmoura.entities.Usuario;
import com.mgmoura.repositories.UsuarioRepository;
import com.mgmoura.services.MD5Service;
import com.mgmoura.services.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MD5Service md5Service;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<AuthResponseDTO> post(@RequestBody @Valid AuthPostRequestDTO dto) {

		AuthResponseDTO response = new AuthResponseDTO();

		try {

			Optional<Usuario> usuario = usuarioRepository.findByEmailAndSenha(dto.getEmail(),
					md5Service.encrypt(dto.getSenha()));

			if (usuario.isPresent()) {

				Usuario item = usuario.get();

				response.setStatus(HttpStatus.OK);
				response.setMensagem("Usuário autenticado com sucesso.");
				response.setNomeUsuario(item.getNome());
				response.setEmailUsuario(item.getEmail());
				response.setAccessToken(tokenService.generateToken(item.getEmail()));
				
			} else {
				
				response.setStatus(HttpStatus.UNAUTHORIZED);
				response.setMensagem("Acesso negado. Usuário ou Senha inválidos.");
			}
			
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMensagem(e.getMessage());
		}

		return ResponseEntity.status(response.getStatus()).body(response);
	}

}
