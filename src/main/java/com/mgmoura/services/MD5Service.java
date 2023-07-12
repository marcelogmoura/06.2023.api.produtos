package com.mgmoura.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class MD5Service {

	public String encrypt(String value) {

		try {
		
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] valueBytes = value.getBytes();

			byte[] digest = md.digest(valueBytes);

			BigInteger number = new BigInteger(1, digest);
			String md5Hash = number.toString(16);

			while (md5Hash.length() < 32) {
				md5Hash = "0" + md5Hash;
			}
			return md5Hash;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			return null;
		}
	}

}
