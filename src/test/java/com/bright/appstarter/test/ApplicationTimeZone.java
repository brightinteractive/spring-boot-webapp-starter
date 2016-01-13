package com.bright.appstarter.test;

import java.time.ZoneId;

import org.springframework.stereotype.Component;

@Component
public class ApplicationTimeZone
{
	/**
	 * At the moment the application only supports a single time zone, which is
	 * defined to be the system time zone of the server.
	 */
	public ZoneId get()
	{
		return ZoneId.systemDefault();
	}
}
