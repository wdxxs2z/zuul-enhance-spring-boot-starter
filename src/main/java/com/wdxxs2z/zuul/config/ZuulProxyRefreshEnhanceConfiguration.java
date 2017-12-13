package com.wdxxs2z.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Configuration;

import com.wdxxs2z.zuul.repository.ZuulRouteStore;
import com.wdxxs2z.zuul.route.RefershProxyRouteLocator;

/**
 * Override the ZuulProxyAutoConfiguration's discoveryRouteLocator() method, use zuulRouteStore instead of serviceRouteMapper.
 * StoreProxyRouteLocator extends DiscoveryClientRouteLocator.
 * @author wdxxs2z
 */
@Configuration
public class ZuulProxyRefreshEnhanceConfiguration extends ZuulProxyAutoConfiguration{

	@Autowired
    private ZuulRouteStore zuulRouteStore;

    @Autowired
    private DiscoveryClient discovery;

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private ServerProperties server;

	@Override
	public DiscoveryClientRouteLocator discoveryRouteLocator() {
		return new RefershProxyRouteLocator(server.getServletPath(), discovery, zuulProperties, zuulRouteStore, discovery.getLocalServiceInstance());
	}
}
