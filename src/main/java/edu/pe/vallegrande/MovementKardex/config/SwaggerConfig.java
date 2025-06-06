package edu.pe.vallegrande.MovementKardex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class SwaggerConfig implements WebFluxConfigurer {

    private final String localUrl = "http://localhost:8085/swagger-ui.html";
    private final String cloudUrl = "https://glorious-yodel-rq97x7rrgj7hrwq-8085.app.github.dev/swagger-ui.html";

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("https://glorious-yodel-rq97x7rrgj7hrwq-8085.app.github.dev/")
                        .description("Servidor de desarrollo"))
                .addServersItem(new Server()
                        .url("http://localhost:8085")
                        .description("Servidor Local"))
                .info(new Info()
                        .title("API REST MS-KARDEX")
                        .description("Especificación de REST API services")
                        .license(new License()
                                .name("Valle Grande")
                                .url("https://vallegrande.edu.pe"))
                        .version("1.0.0")
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    @PostConstruct
    public void printSwaggerUrls() {
        System.out.println("Swagger disponible en:");
        System.out.println("🔹 Local: " + localUrl);
        System.out.println("🔹 Cloud: " + cloudUrl);
    }
}
