package com.bright.appstarter.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil
{
	public static Date startOfDayInDefaultZone(LocalDate localDate)
	{
		return Date.from(
			localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate toLocalDateUsingDefaultZone(Date dateTime)
	{
		return LocalDate.from(dateTime.toInstant().atZone(ZoneId.systemDefault()));
	}

	public static Date dateWithTimeInDefaultZone(int year, Month month, int dayOfMonth, int hour, int minute)
	{
		LocalDateTime ldt = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date startOfDayInDefaultZone(int year, Month month, int dayOfMonth)
	{
		return dateWithTimeInDefaultZone(year, month, dayOfMonth, 0, 0);
	}
}
