package ib.develop.matstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class MatstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatstoreApplication.class, args);
		System.out.println(LocalTime.now());
	}

//	@Bean
//	public Docket swaggerConfiguration(){
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.paths(PathSelectors.ant("/*"))
//				.apis(RequestHandlerSelectors.basePackage("ib.develop"))
//				.build();
//	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedHeaders("Requestor-Type")
						.exposedHeaders("X-Get-Header")
						.maxAge(3600)
						.allowedMethods("GET","POST","DELETE","PUT");
			}
		};
	}

}
