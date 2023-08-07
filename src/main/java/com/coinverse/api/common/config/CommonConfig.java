package com.coinverse.api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua_parser.Parser;

@Configuration
public class CommonConfig {

    @Bean
    Parser parser() {
        return new Parser();
    }
}
