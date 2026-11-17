package tn.esprit.autoloc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoLoc REST API")
                        .version("1.0.0")
                        .description("Documentation interactive des APIs REST pour la gestion de location de véhicules AutoLoc")
                        .contact(new Contact().name("AutoLoc Dev Team"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
