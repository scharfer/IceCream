package com.evan.demo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Validate auth token on admin requests. This is not complete at this time.
 * @author escharfer
 *
 */
public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		//TODO add authentication filter and validate token.
		// At this point token is validated in controller.
		chain.doFilter(request, response);
	}



	public void destroy() {
		//close any resources here
	}

}
