package com.codemaven.liveries.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codemaven.liveries.db.service.LanguagesService;
import com.codemaven.liveries.filter.LanguageFilter;
import com.codemaven.liveries.filter.LiveryConcierge;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class WebConfig
{
	private LanguagesService languageService;
	
	@Bean
	public FilterRegistrationBean<Filter> liveryConciergeRegistrationBean()
	{
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(LiveryConcierge());
		registrationBean.addUrlPatterns("*");
		registrationBean.setName("LiveryConcierge");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public Filter LiveryConcierge()
	{
		return new LiveryConcierge();
	}
	
	@Bean
	public FilterRegistrationBean<Filter> languageFilterRegistrationBean()
	{
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(LanguageFilter());
		registrationBean.addUrlPatterns("*");
		registrationBean.setName("LanguageFilter");
		registrationBean.setOrder(2);
		return registrationBean;
	}

	@Bean
	public Filter LanguageFilter()
	{
		return new LanguageFilter(languageService);
	}
}