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
		FilterRegistration.Dynamic lFilter = pContainer.addFilter("securityFilter",
				new DelegatingFilterProxy("springSecurityFilterChain"));
		lFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true, "/*");
		
		pContainer.addListener(AppHttpSessionListener.class);
	} // void onStartup
}  // class MainWebApplicationInitializer
