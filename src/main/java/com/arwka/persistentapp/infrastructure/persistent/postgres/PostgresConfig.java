package com.arwka.persistentapp.infrastructure.persistent.postgres;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan(basePackages = "com/arwka/persistentapp/infrastructure/persistent/postgres/entity")
public class PostgresConfig {
}
