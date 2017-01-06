package com.flockinger.squidward.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MainRouter extends RouteBuilder {


	//public static String DIRECT_MAIN_ROUTER = "direct:main-router";

	// from(DIRECT_MAIN_ROUTER)

	@Override
	public void configure() {
		from("timer://foo?fixedRate=true&period=3000")
		.process(p -> {
			System.out.println("I'm alive");
		}).end();
	}

}
