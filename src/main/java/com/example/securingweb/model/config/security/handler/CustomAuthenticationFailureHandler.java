package com.example.securingweb.model.config.security.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
		response.getOutputStream()
				.println(String.format(jsonPayload, exception.getMessage(), Calendar.getInstance().getTime()));
		response.sendRedirect("/login");

	}

}
