package com.mpxds.basic.config;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ocpsoft.pretty.PrettyFilter;

@Configuration
public class MpPrettyConfig {
	//
    @SuppressWarnings("rawtypes")
	@Bean
    public FilterRegistrationBean prettyFilter() {
    	//
        @SuppressWarnings("unchecked")
		FilterRegistrationBean rewriteFilter = new FilterRegistrationBean(new PrettyFilter());

        // Filter Forward, Request, Asyc, and Error-related requests.
        rewriteFilter.setDispatcherTypes(DispatcherType.FORWARD,
                DispatcherType.REQUEST,
                DispatcherType.ASYNC,
                DispatcherType.ERROR);

        // Attach the filter to the root URL for the website. e.g.) http://www.example.com/*
        rewriteFilter.addUrlPatterns("/*");

        return rewriteFilter;
    }
}