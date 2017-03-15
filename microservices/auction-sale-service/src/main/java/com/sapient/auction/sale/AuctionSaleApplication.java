package com.sapient.auction.sale;

import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.sapient.auction.sale.config.RestConfig;
import com.sapient.auction.sale.filter.AuthenticationFilter;
import com.sapient.auction.sale.filter.CorsFilter;

/**
 * Created by dpadal on 12/1/2016.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.sapient.auction")
@EntityScan(basePackages = {"com.sapient.auction.sale.entity", "com.sapient.auction.user.entity"})
public class AuctionSaleApplication {


    @Bean
    public RestConfig restConfig() {
        return new RestConfig();
    }

    @Bean
    public Filter corsFilter() {
        return new CorsFilter();
    }

    @Bean
    public Filter securityFilter() {
        return new AuthenticationFilter();
    }


    @Bean
    public javax.validation.Validator validator() {
        return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
        factory.setSessionTimeout((int) TimeUnit.MINUTES.toSeconds(30));
        factory.addServerCustomizers((JettyServerCustomizer) server -> {
            QueuedThreadPool threadPool = (QueuedThreadPool) server.getThreadPool();
            threadPool.setIdleTimeout((int) TimeUnit.MINUTES.toSeconds(30));
            threadPool.setName("SapAuction");
            threadPool.setStopTimeout((int) TimeUnit.MINUTES.toSeconds(30));
            //other custom setting if any.
        });
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuctionSaleApplication.class, args);
    }
}
