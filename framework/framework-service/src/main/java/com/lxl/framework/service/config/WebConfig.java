/**
 * 
 */
package com.lxl.framework.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lxl.framework.service.aop.LogInterceptor;

/**
 * web config
 * 
 * @author Administrator
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注入日志拦截器
		registry.addInterceptor(new LogInterceptor());
	}
}
