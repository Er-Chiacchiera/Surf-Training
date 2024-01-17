package it.uniroma3.siw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class AppConfig {


    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
