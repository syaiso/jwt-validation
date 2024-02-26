package br.com.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.jwt.utils.JwtValidator;

public class JwtValidationTest {

	private JwtValidator jwtValidator;

	private static final Logger logger = LogManager.getLogger(JwtValidationTest.class);

	// Inicializa o objeto antes de cada teste
	@BeforeEach
	public void setUp() {
		jwtValidator = new JwtValidator();
	}

	// Caso 1: JWT válido
	@Test
	public void testValidJwt() throws Exception {
		logger.info("Caso 1: JWT válido");
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
		boolean result = jwtValidator.isValidJwt(jwt);
		logger.info("result: {}", result);
		assertEquals(true, result);
	}

	// Caso 2: JWT inválido
	@Test
	public void testInvalidJwt() {
		logger.info("Caso 2: JWT inválido");
		String jwt = "eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
		boolean result = false;
		try {
			result = jwtValidator.isValidJwt(jwt);
		} catch (Exception e) {
			logger.info("result: {}", result);
			assertEquals(false, result);
		}
	}

	// Caso 3: JWT com claim Name inválida
	@Test
	public void testInvalidNameClaim() throws Exception {
		logger.info("Caso 3: JWT com claim Name inválida");
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs";
		boolean result = jwtValidator.isValidJwt(jwt);
		logger.info("result: {}", result);
		assertEquals(false, result);
	}

	// Caso 4: JWT com mais de 3 claims
	@Test
	public void testExtraClaim() throws Exception {
		logger.info("Caso 4: JWT com mais de 3 claims");
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY";
		boolean result = jwtValidator.isValidJwt(jwt);
		logger.info("result: {}", result);
		assertEquals(false, result);
	}
}
