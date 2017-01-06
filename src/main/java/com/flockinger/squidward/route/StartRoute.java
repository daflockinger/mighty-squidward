package com.flockinger.squidward.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class StartRoute extends RouteBuilder{
	public static String DIRECT_SQUID_START = "direct:squid-start";

	@Override
	public void configure() throws Exception {
		
		/*from(DIRECT_SQUID_START)
		.choice()
		.when(new IsProcessStartableCheck())
			.bean(new CallbackNotification("Started Route"))
			.to(MainRouter.DIRECT_MAIN_ROUTER)
		.otherwise()
			.bean(new CallbackNotification("Process not startable!"))
			.stop().end();*/
	}
}
