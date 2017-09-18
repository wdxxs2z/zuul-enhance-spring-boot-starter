package com.wdxxs2z.zuul.service;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zuul.enhance")
public class EnhanceServiceProperties {
	
	private String description;
	
	private Map<String,Object> store;
	
	private Map<String,Object> dashboard;
	
	private Map<String,Object> fallback;
	
	public Map<String, Object> getStore() {
		return store;
	}

	public void setStore(Map<String, Object> store) {
		this.store = store;
	}

	public Map<String, Object> getDashboard() {
		return dashboard;
	}

	public void setDashboard(Map<String, Object> dashboard) {
		this.dashboard = dashboard;
	}

	public Map<String, Object> getFallback() {
		return fallback;
	}

	public void setFallback(Map<String, Object> fallback) {
		this.fallback = fallback;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
