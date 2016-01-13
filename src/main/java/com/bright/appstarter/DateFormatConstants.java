package com.bright.appstarter;

public class DateFormatConstants
{
	/**
	 * This should be consistent with js-datetimepicker in datetimepicker.js
	 */
	public static final String DATE_TIME_FORMAT = "dd/MMM/yyyy HH:mm";
	/**
	 * This should be consistent with:
	 * <ul>
	 *     <li>application.properties:spring.freemarker.settings.date_format</li>
	 *     <li>messages.properties:typeMismatch.java.util.Date</li>
	 *     <li>js-datepicker in datetimepicker.js</li>
	 * </ul>
	 */
	public static final String DATE_FORMAT = "dd/MMM/yyyy";
}
