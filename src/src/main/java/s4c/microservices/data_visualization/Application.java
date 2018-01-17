package s4c.microservices.data_visualization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@EnableWebMvc
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = "s4c.microservices.data_visualization.model")
@ComponentScan("s4c.microservices.data_visualization")
@EntityScan("s4c.microservices.data_visualization.model.entity")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }   
}
