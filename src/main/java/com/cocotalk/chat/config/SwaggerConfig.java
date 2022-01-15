package com.cocotalk.chat.config;

import com.cocotalk.chat.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Cocotalk-Chat API")
                .description("Oasis팀 Devcamp MVP를 향해!")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("AccessToken", "x-access-token","header");
    }

//    private SecurityContext securityContext() {
//        return springfox
//                .documentation
//                .spi.service
//                .contexts
//                .SecurityContext
//                .builder()
//                .securityReferences(defaultAuth()).build();
//    }

//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("AccessToken", authorizationScopes));
//    }

    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(User.class) // ArgumentResolver
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.cocotalk.chat.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                // .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()));
    }
}