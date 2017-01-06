package com.flockinger.squidward.route;

import org.apache.camel.builder.RouteBuilder;

public abstract class BaseRouteBuilder extends RouteBuilder{
	
	public BaseRouteBuilder(){
		setDeadLetterChannel();
	}
	
	private void setDeadLetterChannel(){
		this.errorHandler(DeadLetterRoute.getErrorHandler());
	}
}
