package com.app.biller;

import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter implements AsyncConfigurer {

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
		mailSender.setHost("inmbzp5180.in.dst.ibm.com");
		mailSender.setPort(25);
		mailSender.setUsername("billeradmin@biller-app.com");
		mailSender.setPassword("billeradmin");

		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.transport.protocol", "smtp");
		mailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(mailProperties);
		return mailSender;
	}

	@Override
	@Bean(name = "threadPoolTaskExecutor")
	public Executor getAsyncExecutor() {
		return new ThreadPoolTaskExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}
}
