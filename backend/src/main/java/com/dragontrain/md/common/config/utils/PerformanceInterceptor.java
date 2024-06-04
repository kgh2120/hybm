package com.dragontrain.md.common.config.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class PerformanceInterceptor implements HandlerInterceptor {

	private ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		long start = System.currentTimeMillis();
		startTime.set(start);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		Long st = this.startTime.get();
		long endTime = System.currentTimeMillis();
		log.info("[{}]{} {}ms", request.getMethod(), request.getRequestURI(), endTime - st);
		startTime.remove();
	}
}
