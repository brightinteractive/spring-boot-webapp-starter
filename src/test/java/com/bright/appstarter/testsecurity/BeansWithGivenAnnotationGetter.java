package com.bright.appstarter.testsecurity;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

public class BeansWithGivenAnnotationGetter
{
	private WebApplicationContext appContext;
	private Class<? extends Annotation> annotationClass;

	public BeansWithGivenAnnotationGetter(WebApplicationContext appContext,
		Class<? extends Annotation> annotationClass)
	{
		this.appContext = appContext;
		this.annotationClass = annotationClass;
	}

	public Collection<Object> getBeans()
	{
		Map<String, Object> beansMap = appContext.getBeansWithAnnotation(this.annotationClass);
		Collection<Object> beans = beansMap.values();
		return beans;
	}

}
