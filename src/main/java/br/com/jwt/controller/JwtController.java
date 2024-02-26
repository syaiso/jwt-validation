package br.com.jwt.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jwt.service.JwtService;

@RestController
@RequestMapping("/api/v1")
public class JwtController {
	
	@Autowired
	private JwtService jwtService;

    private static final Logger logger = LogManager.getLogger(JwtController.class);

	// Método que recebe um JWT como parâmetro e retorna um boolean
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
		try {
            logger.info("JwtController -> validate");

            // Chama o método da classe service que valida o JWT
			boolean isValid = jwtService.validateJwt(jwt);
			
            logger.info("isValid: {}", isValid);
            
            // Retorna o resultado com o status 200 (OK)
			return ResponseEntity.ok(isValid);
		} catch (Exception e) {
            logger.error("Error validate", e);
            
            // Em caso de erro, retorna o status 400 (Bad Request)
			return ResponseEntity.badRequest().body(false);
		}
	}
}