package com.mgmoura.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${jwt.secretkey}")
	private String jwtSecretKey;

	public String generateToken(String emailUsuario) {
		
		return Jwts.builder()
				.setSubject(emailUsuario) 
				.setIssuedAt(new Date()) 
				.signWith(SignatureAlgorithm.HS256, jwtSecretKey)	
				.compact(); 
	}
}



