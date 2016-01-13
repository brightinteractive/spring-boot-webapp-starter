package com.bright.appstarter.time;

import java.time.Instant;

import com.bright.appstarter.time.TimeProviderAdapter;

public class TestTimeProvider extends TimeProviderAdapter
{
	long fakeTime = Instant.parse("2015-10-30T12:00:00.00Z").toEpochMilli();

	@Override
	public long currentTimeMillis()
	{
		return fakeTime;
	}
}
