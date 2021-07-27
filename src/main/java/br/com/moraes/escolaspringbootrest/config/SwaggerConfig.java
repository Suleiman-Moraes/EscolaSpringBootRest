package br.com.moraes.escolaspringbootrest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private ApiProperty apiProperty;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).enable(apiProperty.getSwagger().isShow()).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.moraes.escolaspringbootrest.api.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot REST API").description("\"Web Service\"").version("1.0.0")
				.termsOfServiceUrl("www.google.com")
				.contact(new Contact("Suleiman Alves de Moraes", "https://suleiman-moraes.github.io/",
						"suleimanmoraes@gmail.com"))
				.license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}
}
