package com.wdxxs2z.zuul.route;

import java.util.LinkedHashMap;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

import com.wdxxs2z.zuul.repository.ZuulRouteStore;

/**
 * Override DiscoveryClientRouteLocator's locateRoutes method and this will auto refresh route table.
 * StoreProxyRouteLocator extends DiscoveryClientRouteLocator implements RefreshableRouteLocator.
 * @author wdxxs2z
 */
public class RefershProxyRouteLocator extends DiscoveryClientRouteLocator{
	
	private final ZuulRouteStore store;

	public RefershProxyRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
			ZuulRouteStore store, ServiceInstance serviceIntance) {
		super(servletPath, discovery, properties, serviceIntance);
		this.store = store;
	}

	@Override
	protected LinkedHashMap<String, ZuulRoute> locateRoutes() {
		LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        routesMap.putAll(super.locateRoutes());
        for (ZuulProperties.ZuulRoute route : store.findAll()) {
            routesMap.put(route.getPath(), route);
        }
        return routesMap;
	}	
}
