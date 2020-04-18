package com.dyy.p6e.germ.file;

import com.dyy.p6e.germ.file.config.P6eGermFileConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class P6eGermFileApplication {

    private static final Logger logger = LoggerFactory.getLogger(P6eGermFileApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(P6eGermFileApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(WebServerApplicationContext context) {
        P6eGermFileConfig config = context.getBean(P6eGermFileConfig.class);
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                for (String key : config.getMap().keySet()) {
                    if (config.getMap().get(key).isVisit()) {
                        String resourceHandler = "/static/" + key + "/**";
                        String resourceLocations = config.getBasePath() + config.getMap().get(key).getPath() + "/";
                        registry.addResourceHandler(resourceHandler).addResourceLocations("file:" + resourceLocations);
                        logger.info("[ WebMvcConfigurer ] resource mapping: " + resourceHandler + " => " + resourceLocations);
                    }
                }
            }
        };
    }

    @Bean
    public ApplicationRunner runner(WebServerApplicationContext context) {
        return args -> logger.info("[ P6eGermFileConfig ] => " + context.getBean(P6eGermFileConfig.class).toString());
    }
}
