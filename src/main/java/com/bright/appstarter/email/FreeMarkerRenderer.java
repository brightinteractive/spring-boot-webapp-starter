package com.bright.appstarter.email;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class FreeMarkerRenderer
{
	public static final String FTL_EXTENSION = ".ftl";
	
	@Inject
	private Configuration configuration;

	/**
	 * Returns output rendered by Freemarker.
	 * Uses Spring's FreemarkerTemplateUtils.
	 */
	public String render(String templateName, Object model)
	{
		try
		{
			Template template = configuration.getTemplate(templateName + FTL_EXTENSION);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, (Object) model);
		}
		catch (IOException | TemplateException e)
		{
			throw new RenderingException(e);
		}
	}

	public static class RenderingException extends RuntimeException
	{
		private static final long serialVersionUID = 7543622060315951790L;

		public RenderingException(Exception e)
		{
			super(e);
		}
	}
}
