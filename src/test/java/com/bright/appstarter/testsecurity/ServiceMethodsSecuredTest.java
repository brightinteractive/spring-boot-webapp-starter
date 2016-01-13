package com.bright.appstarter.testsecurity;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.bright.appstarter.stereotype.AppStarterService;
import com.bright.appstarter.test.AppStarterIntegrationTest;
import com.bright.appstarter.user.UserPermissionsService;
import com.bright.appstarter.userlogin.CurrentUserDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@WebAppConfiguration
public class ServiceMethodsSecuredTest
{
	@Inject
	private WebApplicationContext appContext;

	// Service classes we are happy aren't secured:
	private Collection<Class<?>> exceptedClasses = Arrays.asList(CurrentUserDetailsService.class,
		UserPermissionsService.class);

	@Test
	public void testAllAppStarterServicePublicMethodsAnnotatedWithPreAuthorize() throws Exception
	{
		AnnotatedClassesWithMethodsMissingAnnotation getter = new AnnotatedClassesWithMethodsMissingAnnotation(
			appContext,
			AppStarterService.class,
			exceptedClasses);

		Collection<String> methodsMissingPreAuthorize = getter
			.getDescriptionOfMethodsMissingAnnotation(PreAuthorize.class);

		if (methodsMissingPreAuthorize.size() > 0)
		{
			throw new Exception(
				"The following AppStarterService methods are missing the @PreAuthorize annotation: "
					+ methodsMissingPreAuthorize.toString());
		}
	}
}
