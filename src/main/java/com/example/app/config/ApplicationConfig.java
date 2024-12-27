package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.filter.AdminAuthFilter;
import com.example.app.filter.MemberAuthFilter;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
	// エラーメッセージのxmlファイルを指定
	@Override
	public Validator getValidator() {
		var validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	// エラーメッセージのxmlファイルを指定
	@Bean
	public ResourceBundleMessageSource messageSource() {
		var messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("validation");
		return messageSource;
	}

	// 認証用のフィルタの有効化(フィルターが適応されるページを設定)
	// 管理者用
	@Bean
	public FilterRegistrationBean<AdminAuthFilter> authFilter() {
		var bean = new FilterRegistrationBean<AdminAuthFilter>(new AdminAuthFilter());
		bean.addUrlPatterns("/admin/*");
		return bean;
	}

	// 会員用
	@Bean
	public FilterRegistrationBean<MemberAuthFilter> studentAuthFilter() {
		var bean = new FilterRegistrationBean<MemberAuthFilter>(new MemberAuthFilter());
		//TODO フィルターをつけるURLを設定
		bean.addUrlPatterns("/member/*");
		return bean;
	}

	// uploadsフォルダをリソースとして利用可能にする
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/boards/**").addResourceLocations("file:/Users/user/Desktop/workspace/TownCircleSystem/uploads/boards/");
	}
}
