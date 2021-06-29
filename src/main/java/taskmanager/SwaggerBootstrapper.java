package taskmanager;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerBootstrapper {
        @Bean
        public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    protected ApiInfo getApiInfo() {
        Contact contact = new Contact ("Praveen Manvi", "https://github.com/pmanvi", "praveena.manvi@gmail.com");
        ApiInfoBuilder api = new ApiInfoBuilder();
        api.title("Task Manager Swagger API");
        api.description("Example project that generate a Swagger ui client and Swagger json description of Spring boot Rest api. ");
        api.contact(contact);
        api.version("1.0.0");
        return api.build();
    }

    protected ApiSelectorBuilder getBuilder(Docket docket) {
        ApiSelectorBuilder asb = docket.select().apis(RequestHandlerSelectors.any());
        //asb.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")));
        //asb.paths(Predicates.not(PathSelectors.regex("/error.*")));
        return asb;
    }
}
