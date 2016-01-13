package com.bright.appstarter.freemarker;

/*
 * Copyright 2015 Bright Interactive, All Rights Reserved.
 */

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class AppStarterFreeMarkerConfigurer extends FreeMarkerConfigurer
{
	@Inject
	private FreeMarkerProperties properties;

	@Value("${appstarter.spring.freemarker.preferFileSystemAccess}")
	private boolean preferFileSystemAccessSetting;

	@PostConstruct
	public void applyProperties()
	{
		// Copied and pasted from
		// org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration.FreeMarkerConfiguration.applyProperties()
		// :-(
		setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
		setDefaultEncoding(this.properties.getCharsetName());
		Properties settings = new Properties();
		settings.putAll(this.properties.getSettings());
		setFreemarkerSettings(settings);
		// end copied and pasted code

		setPreferFileSystemAccess(preferFileSystemAccessSetting);
	}

	@Override
	protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders)
	{
		logger.info("Using HtmlTemplateLoader to enforce HTML-safe content");
		return new HtmlTemplateLoader(super.getAggregateTemplateLoader(templateLoaders));
	}

	@Override
	protected void postProcessConfiguration(Configuration config) throws IOException,
		TemplateException
	{
		config.setObjectWrapper(new AppStarterObjectWrapper(config.getIncompatibleImprovements()));
	}
}
