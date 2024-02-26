package br.com.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class JwtApplication {

    private static final Logger logger = LogManager.getLogger(JwtApplication.class);

    // Método que starta a aplicação spring boot
	public static void main(String[] args) {
        logger.info("JwtApplication -> main");
		SpringApplication.run(JwtApplication.class, args);
	}

}
