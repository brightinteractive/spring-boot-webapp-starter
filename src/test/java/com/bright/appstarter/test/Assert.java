package com.bright.appstarter.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Date;

import org.exparity.hamcrest.date.DateMatchers;

public class Assert
{
	public static void assertSameDay(LocalDate expected, Date actual)
	{
		assertThat(ensureNotSqlDate(actual),
				   DateMatchers.sameDay(expected));
	}

	/**
	 * Hack to avoid java.lang.UnsupportedOperationException at java.sql.Date.toInstant(Date.java:304)
	 */
	private static Date ensureNotSqlDate(Date actualStartDate)
	{
		if (actualStartDate instanceof java.sql.Date)
		{
			java.sql.Date sqlDate = (java.sql.Date) actualStartDate;
			return new Date(sqlDate.getTime());
		}
		else
		{
			return actualStartDate;
		}
	}
}
