package com.econcept.init;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AppHttpSessionListener implements HttpSessionListener
{

	@Override
	public void sessionCreated(HttpSessionEvent pHttpSessionEvent) 
	{
		// In seconds, so active for 15 minutes
		pHttpSessionEvent.getSession().setMaxInactiveInterval(15 * 60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent pHttpSessionEvent) 
	{
	}
	
}
