package com.wdxxs2z.zuul.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.wdxxs2z.zuul.utils.StringTools;


public class MysqlDriverZuulRouteStore implements ZuulRouteStore {
	
	private static final String DEFAULT_TABLE_NAME = "zuul_routes";
	private final String table;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	public MysqlDriverZuulRouteStore(String table, JdbcTemplate jdbcTemplate) {
		this.table = table;
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public MysqlDriverZuulRouteStore(JdbcTemplate jdbcTemplate) {
		this(DEFAULT_TABLE_NAME, jdbcTemplate);
	}

	@Override
	public List<ZuulRoute> findAll() {
		final String queryString = "select * from " + table;
		List<ZuulRoute> routes = jdbcTemplate.query(queryString, new RowMapper<ZuulRoute>() {

			@Override
			public ZuulRoute mapRow(ResultSet rs, int rowNum) throws SQLException {
			
				String sensitiveHeaders = rs.getString("sensitiveHeaders");
				Set<String> sensitiveHeaderset = new HashSet<String>();
				sensitiveHeaderset.add(sensitiveHeaders);
				
				return new ZuulProperties.ZuulRoute(
						rs.getString("id"),
						rs.getString("path"),
						rs.getString("service_id"),
						rs.getString("url"),
						rs.getBoolean("strip_prefix"),
						rs.getBoolean("retryable"),
						sensitiveHeaderset
	            );
			}
		});
		
		return routes;
	}

	@Override
	public ZuulRoute findById(String id) {
		final String queryString = "select * from " + table + " where id=" + "'" + id + "'";
		List<ZuulRoute> routes = jdbcTemplate.query(queryString, new RowMapper<ZuulRoute>(){
			@Override
			public ZuulRoute mapRow(ResultSet rs, int rowNum) throws SQLException {
				String sensitiveHeaders = rs.getString("sensitiveHeaders");
				Set<String> sensitiveHeaderset = new HashSet<String>();
				sensitiveHeaderset.add(sensitiveHeaders);
				
				return new ZuulProperties.ZuulRoute(
						rs.getString("id"),
						rs.getString("path"),
						rs.getString("service_id"),
						rs.getString("url"),
						rs.getBoolean("strip_prefix"),
						rs.getBoolean("retryable"),
						sensitiveHeaderset
	            );
			}
			
		});
		if (routes.size() == 1) {
			return routes.get(0);
		}
		return null;
	}

	@Override
	public void save(ZuulRoute route) {
		
		Set<String> sensitiveHeaders = new LinkedHashSet<>(
				Arrays.asList("Cookie", "Set-Cookie", "Authorization"));
		
		if (route.getSensitiveHeaders() != null && route.getSensitiveHeaders().size() > 0) {
			sensitiveHeaders = route.getSensitiveHeaders();
		}
		
		String shs = StringTools.converSetToString(sensitiveHeaders);
				
		String url = route.getUrl();
		if (url != null) {
			url = "'" + url + "'";
		}
		final String saveString = "insert into " + table +  " (id,path,service_id,url,strip_prefix,retryable,sensitiveHeaders) values "
				+ "(" + "'" + route.getId() + "'" + "," + "'" + route.getPath() + "'" + "," + "'" + route.getServiceId() + "'" + "," + url + "," + route.isStripPrefix() + "," + route.getRetryable() + "," + shs + ")";
		
		jdbcTemplate.execute(saveString);
		
	}

	@Override
	public void deleteById(String id) {
		final String deleteString = "delete from " + table + " where id=" + "'" + id + "'";
		jdbcTemplate.execute(deleteString);
	}

	@Override
	public ZuulRoute update(String id, ZuulRoute route) {
		
		Set<String> sensitiveHeaders = route.getSensitiveHeaders();
		String shs = StringTools.converSetToString(sensitiveHeaders);
		
		final String updateString = "update " + table 
				+ " set path=?, service_id=?, url=?, strip_prefix=?, retryable=?, sensitiveHeaders=" + shs
				+ " where id = ?";
		jdbcTemplate.update(updateString, route.getPath(),route.getServiceId(),route.getUrl(),route.isStripPrefix(),route.getRetryable(),id);
		return route;
	}

}
