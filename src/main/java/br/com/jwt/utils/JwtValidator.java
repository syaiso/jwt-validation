package br.com.jwt.utils;

import java.util.Base64;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

	// Método que verifica se o JWT é válido
	public boolean isValidJwt(String jwt) throws Exception {

		// Verifica se o JWT tem três partes
		String[] parts = jwt.split("\\.");
		if (parts.length != 3) {
			return false;
		}

		// Verifica se o JWT é HS256
		String header = new String(Base64.getDecoder().decode(parts[0]));
		JSONObject headerJson = new JSONObject(header);
		if (!headerJson.getString("alg").equals("HS256")) {
			return false;
		}

		// Verifica se o JWT contém apenas as claims Name, Role e Seed
		String payload = new String(Base64.getDecoder().decode(parts[1]));
		JSONObject payloadJson = new JSONObject(payload);
		if (payloadJson.length() != 3 || !payloadJson.has("Name") || !payloadJson.has("Role")
				|| !payloadJson.has("Seed")) {
			return false;
		}

		// Verifica se a claim Name possui números
		String name = payloadJson.getString("Name");
		if (Pattern.compile("\\d").matcher(name).find()) {
			return false;
		}

		// Verifica se o tamanho da claim Name
		if (name.length() > 256) {
			return false;
		}

		// Verifica se a claim Role tem um dos valores permitidos
		String role = payloadJson.getString("Role");
		if (!role.equals("Admin") && !role.equals("Member") && !role.equals("External")) {
			return false;
		}

		// Verifica se a claim Seed é um número primo
		int seed = payloadJson.getInt("Seed");
		if (!isPrime(seed)) {
			return false;
		}

		return true;
	}

	// Método que verifica se um número é primo
	public boolean isPrime(int n) {

		if (n < 2) {
			return false;
		}

		if (n % 2 == 0) {
			return n == 2;
		}

		int limit = (int) Math.sqrt(n);
		for (int i = 3; i <= limit; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}
}