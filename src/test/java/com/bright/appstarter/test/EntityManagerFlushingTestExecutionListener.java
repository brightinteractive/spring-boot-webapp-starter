package com.bright.appstarter.test;

import javax.persistence.EntityManager;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;


public class EntityManagerFlushingTestExecutionListener extends AbstractTestExecutionListener
{
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception
	{
		ApplicationContext context = testContext.getApplicationContext();
		EntityManager entityManager = context.getBean(EntityManager.class);
		entityManager.flush();
	}
}
