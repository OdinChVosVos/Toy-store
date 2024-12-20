package ru.astradev.toy_store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Docket journalApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.astradev.toy_store.api")).build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("ToyStoreApp")
                .description("Сервис ToyStoreApp")
                .license("License")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")
                .version(getClass().getPackage().getImplementationVersion())
                .build();
    }
}
