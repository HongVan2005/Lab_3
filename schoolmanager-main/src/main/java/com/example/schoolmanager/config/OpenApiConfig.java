package com.example.schoolmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * OpenAPI / Swagger UI Configuration
 * Cấu hình thông tin chi tiết cho Swagger UI
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8080").description("Development Server"))
                .addServersItem(new Server().url("https://api.example.com").description("Production Server"))
                .info(new Info()
                        .title("School Manager API")
                        .version("1.0.0")
                        .description("API quản lý sinh viên toàn diện cho hệ thống trường học")
                        .contact(new Contact()
                                .name("School Manager Team")
                                .email("support@schoolmanager.com")
                                .url("https://schoolmanager.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
