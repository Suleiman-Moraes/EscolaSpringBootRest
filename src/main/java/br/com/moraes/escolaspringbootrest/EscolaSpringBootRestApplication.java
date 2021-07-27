package br.com.moraes.escolaspringbootrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.moraes.escolaspringbootrest.config.ApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperty.class)
public class EscolaSpringBootRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EscolaSpringBootRestApplication.class, args);
	}

}
