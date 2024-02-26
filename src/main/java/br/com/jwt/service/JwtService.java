package br.com.jwt.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jwt.utils.JwtValidator;

@Service
public class JwtService {

	@Autowired
	private JwtValidator jwtValidator;

	private static final Logger logger = LogManager.getLogger(JwtService.class);

	// Método que valida o JWT
	public boolean validateJwt(String jwt) throws Exception {
		logger.info("JwtService -> validateJwt");

		// Verifica se o JWT é válido
		if (!jwtValidator.isValidJwt(jwt)) {
			logger.info("jwtValidator.isValidJwt(jwt): {}", jwtValidator.isValidJwt(jwt));
			return false;
		}

		logger.info("validateJwt: true");
		return true;
	}
}