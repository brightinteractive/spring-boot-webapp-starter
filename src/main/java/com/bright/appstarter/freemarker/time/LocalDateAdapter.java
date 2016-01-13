package com.bright.appstarter.freemarker.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.bright.appstarter.DateFormatConstants;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class LocalDateAdapter implements TemplateScalarModel
{
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateFormatConstants.DATE_FORMAT);

	private final LocalDate obj;

	public LocalDateAdapter(LocalDate obj)
	{
		this.obj = obj;
	}

	@Override
	public String getAsString() throws TemplateModelException
	{
		if (obj == null)
		{
			return null;
		}

		return FORMATTER.format(obj);
	}
}
