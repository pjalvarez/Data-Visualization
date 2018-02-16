package s4c.microservices.data_visualization;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import s4c.microservices.data_visualization.services.external.UserManagementService;


@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = "s4c.microservices.data_visualization.model")
@ComponentScan("s4c.microservices")
@EntityScan("s4c.microservices.data_visualization.model.entity")
public class Application extends SpringBootServletInitializer {

	public static final String USER_MANAGEMENT_SERVICE_URL = "http://USER-MANAGEMENT";
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {	
        return new RestTemplate();
    }
    @Bean
    public UserManagementService userManagementService() {
        return new UserManagementService(USER_MANAGEMENT_SERVICE_URL);
    }
}


//public class Application {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }   
//}
