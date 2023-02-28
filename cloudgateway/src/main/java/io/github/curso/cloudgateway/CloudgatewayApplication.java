package io.github.curso.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudgatewayApplication.class, args);
	}
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		
		return builder
				.routes()
				.route(rota -> rota.path("/clientes/**").uri("lb://clientes"))
				.route(rota -> rota.path("/cartoes/**").uri("lb://cartoes"))
				.route(rota -> rota.path("/avaliacoes-credito/**").uri("lb://avaliadorcredito"))
				.build();
	}

}
