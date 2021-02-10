/**
 * 
 */
package com.in28minutes.api.gateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sameer
 *
 */
@Configuration
public class ApiGatewayRouter {
	
	/*
	 * Customizing the request URLs through RouteLocator and RouteLocatorBuilder
	 */
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p.path("/get").filters(f -> f.addRequestHeader("MyHeader", "MyURI")
						.addRequestParameter("MyParam", "MyValue")).uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange/"))
				.route(p -> p.path("/currency-conversion/feign/**").uri("lb://currency-conversion/"))
				.route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion/"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion/feign/${segment}")).uri("lb://currency-conversion/"))
				.build();
	}
}
