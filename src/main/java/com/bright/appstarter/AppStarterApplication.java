package com.bright.appstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AppStarterApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(AppStarterApplication.class, args);
	}
}
