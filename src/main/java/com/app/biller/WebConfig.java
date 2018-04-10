package com.app.biller;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.app.biller.interceptor.AuthenticationInterceptor;

@EnableWebMvc
@Configuration
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationInterceptor());
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(20848820);
		return multipartResolver;
	}

	@Bean
	public JavaMailSender getMailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// GMAIL SMTP Configuration
		mailSender.setHost("inmbzp5180.in.dst.ibm.com");
		mailSender.setPort(25);
		mailSender.setUsername("abc");
		mailSender.setPassword("abc");

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;
	}
}
