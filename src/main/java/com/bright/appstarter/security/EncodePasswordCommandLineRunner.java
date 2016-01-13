package com.bright.appstarter.security;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnNotWebApplication
public class EncodePasswordCommandLineRunner implements ApplicationRunner
{
	private static final String OPTION_NAME = "encode.password";

	@Inject
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		if (args.containsOption(OPTION_NAME))
		{
			for (String password : args.getOptionValues(OPTION_NAME))
			{
				final String encodedPassword = passwordEncoder.encode(password);
				System.out.println("encoded password: " + encodedPassword);
			}
		}
		else
		{
			System.err.println("No password to encode supplied");
		}
	}
}
