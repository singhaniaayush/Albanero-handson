package com.test.handson1.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.test.handson1.constants.ApplicationConstants;

@Component
public class AppFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String tenantName = request.getHeader(ApplicationConstants.TENANT_ID);
		MDC.put(ApplicationConstants.TENANT_ID, tenantName);
		filterChain.doFilter(request, response);

	}

}
