package com.flockinger.squidward.model;

import java.io.Serializable;
import java.util.Map;

public class RouteConfig implements Serializable{
	
	private static final long serialVersionUID = -3180084768071253918L;
	
	private Map<String,Object> settings;
	private Map<String, String> externalEndpoints;
	
	public Map<String, Object> getSettings() {
		return settings;
	}
	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}
	public Map<String, String> getExternalEndpoints() {
		return externalEndpoints;
	}
	public void setExternalEndpoints(Map<String, String> externalEndpoints) {
		this.externalEndpoints = externalEndpoints;
	}
}
