package s4c.microservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@PropertySource("classpath:application.properties")
@Configuration
@EnableSwagger2
class SwaggerConfig {

    /**
     * Create Swagger Api configuration
     *
     * @return Swagger Docket
     */
	 @Bean
     public Docket api() {
		 return new Docket(DocumentationType.SWAGGER_2)				 
         .select()                                  
         .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))              
         .paths(PathSelectors.any())                          
         .build();                                          
     }

    /**
     * Generate Api Info
     *
     * @return Swagger API Info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("S4c - data visualization")
                .description("Data visualization Microservice")
                .version("0.1-SNAPSHOT")
                .termsOfServiceUrl("https://es.wikipedia.org/wiki/s4c")
                .license("Open source licensing")
                .licenseUrl("https://help.github.com/articles/open-source-licensing/")
                .build();
    }
}