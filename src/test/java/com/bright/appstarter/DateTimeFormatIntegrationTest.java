package com.bright.appstarter;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import com.bright.appstarter.DateFormatConstants;

import org.junit.Before;
import org.junit.Test;

public class DateTimeFormatIntegrationTest
{
	private DateTimeFormatter dateTimeFormat;

	@Before
	public void setUp() throws Exception
	{
		dateTimeFormat = DateTimeFormatter.ofPattern(DateFormatConstants.DATE_TIME_FORMAT);
	}

	@Test
	public void testHourHasLeadingZero()
	{
		assertEquals(
			"28/Jul/2015 06:52",
			dateTimeFormat.format(LocalDateTime.of(2015, Month.JULY, 28, 6, 52)));
	}

	@Test
	public void testHourIs24Hour()
	{
		assertEquals(
			"28/Jul/2015 18:52",
			dateTimeFormat.format(LocalDateTime.of(2015, Month.JULY, 28, 18, 52)));
	}

	@Test
	public void testZeroHourIsZero()
	{
		assertEquals(
			"28/Jul/2015 00:52",
			dateTimeFormat.format(LocalDateTime.of(2015, Month.JULY, 28, 00, 52)));
	}
}
