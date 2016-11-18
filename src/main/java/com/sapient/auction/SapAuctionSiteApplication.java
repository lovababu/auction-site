package com.sapient.auction;

import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring boot strapping class.
 *
 * Created by dpadal on 11/11/2016.
 */

import com.sapient.auction.common.config.RestConfig;
import com.sapient.auction.common.filter.CorsFilter;
import com.sapient.auction.security.CustomLogoutHandler;

@SpringBootApplication
@ComponentScan(basePackages = "com.sapient.auction")
@EntityScan(basePackages = {"com.sapient.auction.sale.entity", "com.sapient.auction.user.entity"})
public class SapAuctionSiteApplication {


	@Bean
	public RestConfig restConfig() {
		return new RestConfig();
	}

	@Bean
	public Filter corsFilter() {
		return new CorsFilter();
	}

	//Validator bean to validate the input json.
	@Bean
	public javax.validation.Validator validator() {
		return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
		factory.setSessionTimeout((int) TimeUnit.MINUTES.toSeconds(30));
		factory.addServerCustomizers(new JettyServerCustomizer(){

			@Override
			public void customize(Server server) {
				QueuedThreadPool threadPool = (QueuedThreadPool) server.getThreadPool();
				threadPool.setIdleTimeout((int) TimeUnit.MINUTES.toSeconds(30));
				threadPool.setName("SapAuction");
				threadPool.setStopTimeout((int) TimeUnit.MINUTES.toSeconds(30));
				//other custom setting if any.
			}
		});
		return factory;
	}
	
	@Bean
	public CustomLogoutHandler customLogoutHandler() {
	    return new CustomLogoutHandler();
	}

	public static void main(String[] args) {
		SpringApplication.run(SapAuctionSiteApplication.class, args);
	}
}
