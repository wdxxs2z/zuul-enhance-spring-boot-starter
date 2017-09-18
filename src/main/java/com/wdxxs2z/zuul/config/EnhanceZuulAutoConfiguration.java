package com.wdxxs2z.zuul.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wdxxs2z.zuul.repository.MysqlDriverZuulRouteStore;
import com.wdxxs2z.zuul.repository.ZuulRouteStore;
import com.wdxxs2z.zuul.service.EnhanceService;
import com.wdxxs2z.zuul.service.EnhanceServiceProperties;
import com.wdxxs2z.zuul.ui.DashboardController;


/**
 * Register ZuulRouteStore and StoreService Bean & Set the properties to them.
 * @author wdxxs2z
 */
@Configuration
@EnableConfigurationProperties(value = EnhanceServiceProperties.class)
@ConditionalOnClass(EnhanceService.class)
public class EnhanceZuulAutoConfiguration {
	
	@Autowired                                                                                                                              
    private EnhanceServiceProperties enhanceServiceProperties;                                                                                  
                                                                                                                                            
    @Bean                                                                                                                                   
    @ConditionalOnMissingBean(EnhanceService.class)                                                                                           
    public EnhanceService enhanceService() {                                                                                                    
    	EnhanceService enhanceService = new EnhanceService();                                                                                     
    	enhanceService.setDescription(enhanceServiceProperties.getDescription());                                                                               
        return enhanceService;                                                                                                          
    }
    
    @Bean
	@ConditionalOnProperty(prefix="zuul.enhance.dashboard", value = "enabled", havingValue = "true", matchIfMissing = false)
    public DashboardController dashboardController() {
        return new DashboardController();
    }
	
	@Bean
	@ConditionalOnProperty(prefix="zuul.enhance.store.mysql", value = "enabled", havingValue = "true", matchIfMissing = false)
    public ZuulRouteStore mysqlZuulRouteStore(JdbcTemplate jdbcTemplate) {
        return new MysqlDriverZuulRouteStore(jdbcTemplate);
    }
	
	@Bean
	@ConditionalOnProperty(prefix="zuul.enhance.fallback", value = "enabled", havingValue = "true", matchIfMissing = false)
	public ZuulFallbackProvider fallbackProvider() {
		
		return new ZuulFallbackProvider() {
			
			@Override
			public String getRoute() {
				return "*";
			}
			
			@Override
			public ClientHttpResponse fallbackResponse() {
				return new ClientHttpResponse() {

					@Override
					public InputStream getBody() throws IOException {
						return new ByteArrayInputStream("fallback".getBytes());
					}

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						return headers;
					}

					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.OK;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return 200;
					}

					@Override
					public String getStatusText() throws IOException {
						return "OK";
					}

					@Override
					public void close() {
						
					}
					
				};
			}
		};
	}
	
}
