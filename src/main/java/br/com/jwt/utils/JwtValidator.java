package br.com.jwt.utils;

import java.util.Base64;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private static final Logger logger = LogManager.getLogger(JwtValidator.class);

	// Método que verifica se o JWT é válido
	public boolean isValidJwt(String jwt) throws Exception {

		logger.info("JwtValidator -> isValidJwt");

		// Verifica se o JWT tem três partes
		String[] parts = jwt.split("\\.");
		if (parts.length != 3) {
			logger.info("JWT não tem três partes");
			return false;
		}

		// Verifica se o JWT é HS256
		logger.info("Decodifica parts[0] que esta em Base64");
		String header = new String(Base64.getDecoder().decode(parts[0]));
		logger.info("Converte a string header num JSONObject");
		JSONObject headerJson = new JSONObject(header);
		if (!headerJson.getString("alg").equals("HS256")) {
			logger.info("Header do JWT não usa algoritmo HS256");
			return false;
		}

		// Verifica se o JWT contém apenas as claims Name, Role e Seed
		logger.info("Decodifica parts[1] que esta em Base64");
		String payload = new String(Base64.getDecoder().decode(parts[1]));
		logger.info("Converte a string payload num JSONObject");
		JSONObject payloadJson = new JSONObject(payload);
		if (payloadJson.length() != 3 || !payloadJson.has("Name") || !payloadJson.has("Role")
				|| !payloadJson.has("Seed")) {
			logger.info("Qtde de claims diferente de 3 ou claims não são Name, Role ou Seed");
			return false;
		}

		// Verifica se a claim Name possui números
		String name = payloadJson.getString("Name");
		if (Pattern.compile("\\d").matcher(name).find()) {
			logger.info("Claim Name possui números: {}", name);
			return false;
		}

		// Verifica se o tamanho da claim Name
		if (name.length() > 256) {
			logger.info("Claim Name tem tamanho maior que 256 caracteres: {}", name.length());
			return false;
		}

		// Verifica se a claim Role tem um dos valores permitidos
		String role = payloadJson.getString("Role");
		if (!role.equals("Admin") && !role.equals("Member") && !role.equals("External")) {
			logger.info("Claim Role não tem os valores permitidos (Admin, Member ou External): {}", role);
			return false;
		}

		// Verifica se a claim Seed é um número primo
		int seed = payloadJson.getInt("Seed");
		if (!isPrime(seed)) {
			logger.info("Claim Seed não é um número primo: {}", seed);
			return false;
		}

		logger.info("JWT válida: {}", jwt);
		return true;
	}

	// Método que verifica se um número é primo
	private boolean isPrime(int number) {
		
		logger.info("JwtValidator -> isPrime");

		//Verifica se number é 0 ou 1 que não são números primos
        if (number <= 1) {
			logger.info("0 ou 1 não são números primos, number: {}", number);
            return false;
        }

		//limit recebe a parte inteira da raiz quadrada de number
        //para minimizar o número de iterações é feita a raiz quadrada de number
		int limit = (int) Math.sqrt(number);
		
		//Cada iteração verifica se number é divisível por um número a partir de 2
		//se number for divisível por qualquer valor de i (i <= limit), então ele não é um número primo
		//um número é primo se ele só é divisível por 1 e por ele mesmo
        for (int i = 2; i <= limit; i++) {
            if (number % i == 0) {
    			logger.info("number {} não é número primo por ser divisível por {}", number, i);
                return false;
            }
        }

		logger.info("number {} é número primo", number);
        return true;
	}
}