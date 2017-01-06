package com.flockinger.squidward.route;

import org.springframework.stereotype.Component;

@Component
public class MainRouter extends BaseRouteBuilder {

	@Override
	public void configure() {
		from("timer://foo?fixedRate=true&period=3000")
		.process(p -> {
			System.out.println("I'm alive");
		}).end();
	}

}
