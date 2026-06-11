package com.local_cart.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI settings() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local server for local_cart API Documentation");

        Info info = new Info();
        info.setTitle("local_cart_product_API");
        info.setVersion("1.0");
        info.setDescription("API documentation for product service");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
