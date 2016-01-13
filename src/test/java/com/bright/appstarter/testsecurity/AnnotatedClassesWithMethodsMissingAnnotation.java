package com.bright.appstarter.testsecurity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.web.context.WebApplicationContext;

public class AnnotatedClassesWithMethodsMissingAnnotation
{
	private WebApplicationContext appContext;
	private Class<? extends Annotation> annotationOnClass;
	private Collection<Class<?>> exceptedClasses;

	public AnnotatedClassesWithMethodsMissingAnnotation(WebApplicationContext appContext,
		Class<? extends Annotation> annotationOnClass,
		Collection<Class<?>> exceptedClasses)
	{
		this.appContext = appContext;
		this.annotationOnClass = annotationOnClass;
		this.exceptedClasses = exceptedClasses;
	}

	public Collection<String> getDescriptionOfMethodsMissingAnnotation(
		Class<? extends Annotation> annotationOnMethod)
	{
		Collection<Class<?>> serviceClasses = getClassesWithAnnotation();

		Collection<String> methodsMissingPreAuthorize = new ArrayList<>();
		for (Class<?> serviceClass : serviceClasses)
		{
			methodsMissingPreAuthorize.addAll(getNamesOfPublicNonInheritedMethodsMissingAnnotation(
				serviceClass,
				annotationOnMethod));
		}

		return methodsMissingPreAuthorize;
	}

	private Collection<Class<?>> getClassesWithAnnotation()
	{
		Collection<Object> serviceBeanProxies = new BeansWithGivenAnnotationGetter(appContext,
			this.annotationOnClass).getBeans();

		Collection<Class<?>> serviceClasses = new ArrayList<>();

		for (Object bean : serviceBeanProxies)
		{
			Class<?> ultimateClass = AopProxyUtils.ultimateTargetClass(bean); // Get
																				// the
																				// actual
																				// class
																				// (not
																				// the
																				// proxy).

			if (!this.exceptedClasses.contains(ultimateClass))
			{
				serviceClasses.add(ultimateClass);
			}
		}

		return serviceClasses;
	}

	private Collection<String> getNamesOfPublicNonInheritedMethodsMissingAnnotation(
		Class<?> serviceClass,
		Class<? extends Annotation> annotationClass)
	{
		Collection<String> methodNames = new ArrayList<>();
		for (Method method : serviceClass.getDeclaredMethods())
		{
			if (Modifier.isPublic(method.getModifiers())
				&& !method.isAnnotationPresent(annotationClass))
			{
				methodNames.add(serviceClass + "." + method.getName());
			}
		}

		return methodNames;
	}

}
