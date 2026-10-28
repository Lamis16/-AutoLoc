package tn.esprit.tpautoloc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI autoLocOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoLoc API")
                        .description("API REST pour la plateforme de gestion de location de véhicules multi-agences AutoLoc.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Lamis Touhami")
                                .email("lamis.touhami@esprit.tn")));
    }
}