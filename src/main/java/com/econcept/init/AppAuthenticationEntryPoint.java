package com.econcept.init;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class AppAuthenticationEntryPoint extends BasicAuthenticationEntryPoint
{
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse pResponse, AuthenticationException authException)
			throws IOException, ServletException {
        pResponse.addHeader("WWW-Authenticate", "xBasic realm=\"" + getRealmName() +"\"");
		pResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}  // commence

}
