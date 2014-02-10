/**
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * 
 * Permission is hereby granted, free of charge, 
 * to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom 
 * the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * The above copyright notice and this permission notice 
 * shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY 
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS 
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */


package com.econcept.init;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.econcept.filter.UnicodeFilter;

public class MainWebAppplicationInitializer implements
		WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext pContainer) throws ServletException {
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext lRootContext = new AnnotationConfigWebApplicationContext();
		lRootContext.scan("com.econcept.init");

		// Manage the lifecycle of the root application context
		pContainer.addListener(new ContextLoaderListener(lRootContext));
		
		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic lDispatcher = pContainer.addServlet(
				"CFXServlet", CXFServlet.class);
		lDispatcher.addMapping("/rest/*");
		
		// Apply Spring OAuthSecurity to both forward and request dispatcher
		FilterRegistration.Dynamic lFilter = pContainer.addFilter("unicodeFilter",
				new UnicodeFilter());
		lFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");
		
		// Apply Spring OAuthSecurity to both forward and request dispatcher
		lFilter = pContainer.addFilter("securityFilter",
				new DelegatingFilterProxy("springSecurityFilterChain"));
		lFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");
		
		pContainer.addListener(AppHttpSessionListener.class);
		
		
	} // void onStartup
}  // class MainWebApplicationInitializer
