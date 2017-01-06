package com.flockinger.squidward.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.camel.Endpoint;

public class RoutePath implements Serializable{

	private static final long serialVersionUID = 7909760071036836860L;
	public static final String HEADER_ROUTE_PATH = "squidRoutePath";
	
	private Map<String, RouteConfig> routePoints;
	private String currentRoutePoint;
	private String defaultEndPoint;
	private Sender sender;
	private boolean activateStatusNotification;

	public Map<String, RouteConfig> getRoutePoints() {
		return routePoints;
	}
	public void setRoutePoints(Map<String, RouteConfig> routePoints) {
		this.routePoints = routePoints;
	}
	public String getCurrentRoutePoint() {
		return currentRoutePoint;
	}
	public void setCurrentRoutePoint(String currentRoutePoint) {
		this.currentRoutePoint = currentRoutePoint;
	}
	public String getDefaultEndPoint() {
		return defaultEndPoint;
	}
	public void setDefaultEndPoint(String defaultEndPoint) {
		this.defaultEndPoint = defaultEndPoint;
	}
	public Sender getSender() {
		return sender;
	}
	public void setSender(Sender sender) {
		this.sender = sender;
	}
	public boolean isActivateStatusNotification() {
		return activateStatusNotification;
	}
	public void setActivateStatusNotification(boolean activateStatusNotification) {
		this.activateStatusNotification = activateStatusNotification;
	}
}
