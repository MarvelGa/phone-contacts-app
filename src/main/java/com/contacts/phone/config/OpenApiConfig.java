package com.contacts.phone.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI phoneContactsOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Phone Contacts")
                        .description("The application will allow users to easily create/update/find/delete" +
                                "contacts.")
                        .version("1.0.0"));
    }

}

