package com.bright.appstarter.freemarker;

import java.time.LocalDate;

import com.bright.appstarter.freemarker.time.LocalDateAdapter;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

public class AppStarterObjectWrapper extends DefaultObjectWrapper
{
	public AppStarterObjectWrapper(Version incompatibleImprovements)
	{
		super(incompatibleImprovements);
	}

	@Override
	protected TemplateModel handleUnknownType(Object obj) throws TemplateModelException
	{
		if (obj instanceof LocalDate)
		{
			return new LocalDateAdapter((LocalDate) obj);
		}
		return super.handleUnknownType(obj);
	}
}
