package com.wdxxs2z.zuul.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wdxxs2z.zuul.repository.ZuulRouteStore;

@RestController
public class DashboardController{
	
	@Autowired
	private ZuulRouteStore store;

	@RequestMapping(value="/route", method=RequestMethod.GET)
	public List<ZuulRoute> getAllRoutes() {
		List<ZuulRoute> routes = store.findAll();
		return routes;
	}
	
	@RequestMapping(value="/route/{id}", method=RequestMethod.GET)
	public ZuulRoute getOneRouteById(@PathVariable(value="id") String id) {
		ZuulRoute zuulRoute = store.findById(id);
		return zuulRoute;
	}
	
	@RequestMapping(value="/route", method=RequestMethod.POST)
	public ZuulRoute saveRoute(@RequestBody ZuulRoute zuulRoute) {
		store.save(zuulRoute);
		return zuulRoute;
	}
	
	@RequestMapping(value="/route/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable(value="id") String id) {
		store.deleteById(id);
	}
	
	@RequestMapping(value="/route/{id}", method=RequestMethod.PUT)
	public ZuulRoute updateOneRoute(@PathVariable(value="id") String id, @RequestBody ZuulRoute zuulRoute) {
		System.out.println(zuulRoute);
		ZuulRoute route = store.update(id, zuulRoute);
		return route;
	}

}
