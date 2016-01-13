package com.bright.appstarter.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bright.appstarter.AppStarterApplication;

@SpringBootApplication(scanBasePackageClasses = AppStarterApplication.class)
@EnableTransactionManagement
public class EncodePasswordApplication
{
	public static void main(String[] args)
	{
		final SpringApplication application = new SpringApplication(EncodePasswordApplication.class);
		application.setWebEnvironment(false);
		application.run(args);
	}
}
