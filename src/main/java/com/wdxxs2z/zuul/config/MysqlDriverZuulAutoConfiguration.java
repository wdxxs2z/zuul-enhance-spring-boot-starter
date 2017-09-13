package com.wdxxs2z.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wdxxs2z.zuul.repository.MysqlDriverZuulRouteStore;
import com.wdxxs2z.zuul.repository.ZuulRouteStore;
import com.wdxxs2z.zuul.service.StoreService;
import com.wdxxs2z.zuul.service.StoreServiceProperties;


/**
 * Register ZuulRouteStore and StoreService Bean & Set the properties to them.
 * @author wdxxs2z
 */
@Configuration
@EnableConfigurationProperties(value = StoreServiceProperties.class)
@ConditionalOnClass(StoreService.class)
@ConditionalOnProperty(prefix="zuul.store.mysql", value = "enabled", havingValue = "true", matchIfMissing = false)
public class MysqlDriverZuulAutoConfiguration {
	
	@Autowired                                                                                                                              
    private StoreServiceProperties storeServiceProperties;                                                                                  
                                                                                                                                            
    @Bean                                                                                                                                   
    @ConditionalOnMissingBean(StoreService.class)                                                                                           
    public StoreService storeService() {                                                                                                    
    	StoreService storeService = new StoreService();                                                                                     
    	storeService.setDescription(storeServiceProperties.getDescription());                                                                               
        return storeService;                                                                                                            
    }
	
	@Bean
    @ConditionalOnMissingBean
    public ZuulRouteStore mysqlZuulRouteStore(JdbcTemplate jdbcTemplate) {
        return new MysqlDriverZuulRouteStore(jdbcTemplate);
    }
	
}
