package com.mgmoura.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.mgmoura.entities.Usuario;
import com.mgmoura.repositories.UsuarioRepository;
import com.mgmoura.services.MD5Service;

@Component
public class LoadData implements ApplicationRunner{

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MD5Service md5Service;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		usuarioRepository.save(new Usuario(1, "Joe", "joe@email", md5Service.encrypt("Admin@123")));

		usuarioRepository.save(new Usuario(2, "Ana", "ana@email", md5Service.encrypt("Admin@123")));

		
	}

}
