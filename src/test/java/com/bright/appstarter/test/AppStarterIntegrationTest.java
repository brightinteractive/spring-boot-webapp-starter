package com.bright.appstarter.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;

import com.bright.appstarter.AppStarterApplication;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringApplicationConfiguration(classes = AppStarterApplication.class)
@ActiveProfiles("test")
public @interface AppStarterIntegrationTest
{
}
