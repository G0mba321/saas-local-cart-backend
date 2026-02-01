package com.local_cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.local_cart.config.TestSecurityConfig;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class BaseIntegrationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @LocalServerPort
    private int localServerPort;

    @PostConstruct
    public void init() {
        RestAssured.port = localServerPort;
        objectMapper.findAndRegisterModules();
    }
}
