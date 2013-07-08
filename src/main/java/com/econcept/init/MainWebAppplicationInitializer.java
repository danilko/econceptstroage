package com.econcept.init;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class MainWebAppplicationInitializer implements WebApplicationInitializer
{
	@Override
	public void onStartup(ServletContext pContainer) throws ServletException 
	{
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext lRootContext = new AnnotationConfigWebApplicationContext();
		lRootContext.scan("com.econcept.init");
		
		// Manage the lifecycle of the root application context
		pContainer.addListener(new ContextLoaderListener(lRootContext));
		
		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic lDispatcher = pContainer.addServlet("CFXServlet", new CXFServlet());
		lDispatcher.setLoadOnStartup(1);
		lDispatcher.addMapping("/service/*");
	}

}
