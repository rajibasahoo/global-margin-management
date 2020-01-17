package com.luxoft.globalmanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Configuration for Swagger rest documentation API.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.luxoft.response"
})
@EnableSwagger2
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luxoft.globalmanagement"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/docs/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Global Management API",
                "This API allows for write and reads of the oil prices and find Revenue info", "1.0",
                null,
                new Contact("Team Luxoft", "https://confluence.luxoft.com/Oil+Revenue", "rajiba.sahoo@luxoft.com"),
                null,
                null,
                Collections.emptyList()
        );
    }
}
