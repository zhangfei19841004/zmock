package com.zf;

import com.zf.config.MockWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableWebMvc
public class ZmockApplication {

	/*@Bean
	public ServletRegistrationBean mockAPIServletRegistrationBean() {
		//注解扫描上下文
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		//applicationContext.register(HttpConverterConfig.class);
		//base package
		applicationContext.scan("com.zf.controller.mock");
		//通过构造函数指定dispatcherServlet的上下文
		DispatcherServlet servlet = new DispatcherServlet(applicationContext);

		//用ServletRegistrationBean包装servlet
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet);
		registrationBean.setLoadOnStartup(1);
		//指定urlmapping
		registrationBean.addUrlMappings("/");
		//指定name，如果不指定默认为dispatcherServlet
		registrationBean.setName("mock");
		return registrationBean;
	}*/

    @Bean
    public ServletRegistrationBean mockWebServletRegistrationBean() {
        //注解扫描上下文
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(MockWebConfig.class);
        //base package
        applicationContext.scan("com.zf.web");
        //通过构造函数指定dispatcherServlet的上下文
        DispatcherServlet servlet = new DispatcherServlet(applicationContext);
        //用ServletRegistrationBean包装servlet
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet);
        registrationBean.setLoadOnStartup(1);
        //指定urlmapping
        registrationBean.addUrlMappings("/zmock/*");
        //指定name，如果不指定默认为dispatcherServlet
        registrationBean.setName("mockweb");
        return registrationBean;
    }

	/*@Bean
	public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new CharacterEncodingFilter());
		registration.addUrlPatterns("");
		registration.addInitParameter("encoding", "UTF-8");
		registration.addInitParameter("forceEncoding", "true");
		registration.setName("SpringEncodingFilter");
		registration.setOrder(1);
		return registration;
	}*/

	/*@Bean
	public ServletListenerRegistrationBean listenerRegistrationBean() {
		ServletListenerRegistrationBean registration = new ServletListenerRegistrationBean();
		registration.setListener(new ContextLoaderListener());
		registration.setOrder(1);
		return registration;
	}*/

	/*@Bean
	public ServletListenerRegistrationBean introspectorCleanupListenerRegistrationBean() {
		ServletListenerRegistrationBean registration = new ServletListenerRegistrationBean();
		registration.setListener(new IntrospectorCleanupListener());
		registration.setOrder(1);
		return registration;
	}*/

    public static void main(String[] args) {
        SpringApplication.run(ZmockApplication.class, args);
    }
}
