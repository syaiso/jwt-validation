package br.com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jwt.service.JwtService;

@RestController
@RequestMapping("/api")
public class JwtController {

	@Autowired
	private JwtService jwtService;

	// Método que recebe um JWT como parâmetro e retorna um boolean
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
		try {
			// Chama o método da classe service que valida o JWT
			boolean isValid = jwtService.validateJwt(jwt);
			// Retorna o resultado com o status 200 (OK)
			return ResponseEntity.ok(isValid);
		} catch (Exception e) {
			// Em caso de erro, retorna o status 400 (Bad Request)
			return ResponseEntity.badRequest().body(false);
		}
	}
}