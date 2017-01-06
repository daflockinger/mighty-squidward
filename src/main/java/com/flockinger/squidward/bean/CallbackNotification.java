package com.flockinger.squidward.bean;

import org.apache.camel.Handler;
import org.apache.camel.Header;

import com.flockinger.squidward.model.RoutePath;

public class CallbackNotification {
	
	private Object notification;
	
	public CallbackNotification(Object notification){
		this.notification = notification;
	}

	@Handler
	public void sendNotification(@Header("routePath") RoutePath path){
		//TODO insert code
	}
}
