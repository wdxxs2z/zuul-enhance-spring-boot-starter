package com.wdxxs2z.zuul.service;

import java.util.Map;

public class EnhanceService {
	
	private String description;
	
	private Map<String,Object> store;
	
	private Map<String,Object> dashboard;
	
	private Map<String,Object> fallback;
	
	public String printDescription() {
		return "Print description: " + description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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
}
