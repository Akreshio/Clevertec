package ru.clevertec.clevertec.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
public class ConnectionSettings {

    private static int DEFAULT_MAX_POOL_SIZE = 5;

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int jdbcMaxPoolSize = DEFAULT_MAX_POOL_SIZE;
}
