package com.yash.es.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class EsConfig {

	@Autowired
	Environment env;

	/*@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(postPaths()).build();
	}

	private Predicate<String> postPaths() {
		return regex("/employees.*");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("JavaInUse API")
				.description("JavaInUse API reference for developers")
				.termsOfServiceUrl("http://javainuse.com")
				.contact("javainuse@gmail.com").license("JavaInUse License")
				.licenseUrl("javainuse@gmail.com").version("1.0").build();
	}*/

	
	 @Bean
	    public Docket productApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select().apis(RequestHandlerSelectors.basePackage("com.yash.es.controller"))
	                .paths(regex("/employees.*"))
	                .build();
	    }
	 
	@SuppressWarnings("resource")
	@Bean
	public TransportClient transportClient() {
		final String host = env.getProperty("host");
		TransportClient client = null;
		Settings setting = Settings.builder().put("cluster.name", env.getProperty("clustername")).build();
		try {
			client = new PreBuiltTransportClient(setting)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(host), 9300));
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}

		// createIndex(client);

		return client;
	}

	@Bean
	public Gson getGson() {

		Gson gson = null;
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.serializeNulls();
			gson = gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return gson;

	}
}
