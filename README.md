# Zuul Enhance Spring Boot Starter

Provider a simple ui,a custom fallback provider,and use mysql store the routes.

## Import starter into your project
```
<dependency>
	<groupId>com.wdxxs2z</groupId>
	<artifactId>zuul-enhance-spring-boot-starter</artifactId>
	<version>0.0.3</version>
</dependency>
```

## Configure your zuul application yml
```
zuul:
  ignoredServices: '*'
  enhance:
    store:
      mysql:
        enabled: true
    description: "This is zuul enhance support."
    fallback:
      enabled: true
    dashboard:
      enabled: true
```

## Configure your mysql store
```
DROP TABLE IF EXISTS `zuul_routes`;
CREATE TABLE `zuul_routes` (
  `id` varchar(128) NOT NULL,
  `path` varchar(128) DEFAULT NULL,
  `service_id` varchar(128) DEFAULT NULL,
  `url` varchar(254) DEFAULT NULL,
  `strip_prefix` boolean DEFAULT true,
  `retryable` boolean DEFAULT false,
  `sensitiveHeaders` set('Cookie','Set-Cookie','Authorization') default '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## Start your zuul api gateway