package com.bright.appstarter.time;

import java.util.Date;

public interface TimeProvider
{
	long currentTimeMillis();
	Date currentTimeAsDate();
}
