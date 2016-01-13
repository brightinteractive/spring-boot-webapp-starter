package com.bright.appstarter.time;

import org.springframework.stereotype.Component;

@Component
public class DefaultTimeProvider extends TimeProviderAdapter
{
	@Override
	public long currentTimeMillis()
	{
		return System.currentTimeMillis();
	}
}
