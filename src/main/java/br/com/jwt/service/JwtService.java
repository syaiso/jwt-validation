package br.com.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jwt.utils.JwtValidator;

@Service
public class JwtService {

	@Autowired
	private JwtValidator jwtValidator;

	// Método que valida o JWT
	public boolean validateJwt(String jwt) throws Exception {
		// Verifica se o JWT é válido
		if (!jwtValidator.isValidJwt(jwt)) {
			return false;
		}

		return true;
	}
}