package com.coinverse.api;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

import java.security.*;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public class ApiApplication {

	@PostConstruct
	public void init() {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
