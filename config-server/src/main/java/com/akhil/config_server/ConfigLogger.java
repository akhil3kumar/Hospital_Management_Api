package com.akhil.config_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class ConfigLogger implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.datasource.url:NOT_FOUND}")
    private String datasourceUrl;

    @Override
    public void run(String... args) {
        logger.info("Datasource URL: {}", datasourceUrl);
    }
}