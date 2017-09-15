package com.wdxxs2z.zuul.repository;

import java.util.List;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

public interface ZuulRouteStore{
	
	List<ZuulProperties.ZuulRoute> findAll();
	
	ZuulProperties.ZuulRoute findById(String id);
	
	void save(ZuulProperties.ZuulRoute route);
	
	ZuulProperties.ZuulRoute update(String id, ZuulProperties.ZuulRoute route);
	
	void deleteById(String id);
}
