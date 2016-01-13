package com.bright.appstarter;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppStarterTestConfiguration
{
	@Bean
	@Profile("test")
	public FlywayMigrationStrategy cleanMigrateStrategy()
	{
		return flyway -> {
			flyway.clean();
			flyway.migrate();
		};
	}
}
