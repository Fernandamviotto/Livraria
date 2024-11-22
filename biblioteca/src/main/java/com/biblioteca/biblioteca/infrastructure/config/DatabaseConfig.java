package com.biblioteca.biblioteca.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.projeto.biblioteca.domain.repository")
public class DatabaseConfig {
    // Configurações de banco de dados
}
