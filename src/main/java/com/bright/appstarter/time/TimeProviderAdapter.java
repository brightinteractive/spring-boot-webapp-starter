package com.bright.appstarter.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class TimeProviderAdapter implements TimeProvider
{
	public Date currentTimeAsDate()
	{
		return new Date(currentTimeMillis());
	}

	public LocalDate currentTimeAsLocalDateUsingDefaultTimeZone()
	{
		return LocalDate.from(Instant.ofEpochMilli(currentTimeMillis()).atZone(ZoneId.systemDefault()));
	}
}
