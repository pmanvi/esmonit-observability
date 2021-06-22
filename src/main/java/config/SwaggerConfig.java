package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.swagger2.mappers.LicenseMapper;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket publicApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .select()
                //.apis(RequestHandlerSelectors.basePackage("esmonit.endpoints"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                //.useDefaultResponseMessages(false)
                ;
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("MicroService - REST API")
                .contact(new Contact("pmanvi@outlook.com","",""))
                .license("Praveen License")
                .version("1.0")
                .description("Microservices Architecture - Design and Observability ")
                .build();
    }

}
