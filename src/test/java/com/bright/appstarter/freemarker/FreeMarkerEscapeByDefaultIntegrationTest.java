package com.bright.appstarter.freemarker;

/*
 * Copyright 2014-2015 Bright Interactive, All Rights Reserved.
 */

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bright.appstarter.test.AppStarterIntegrationTest;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@AppStarterIntegrationTest
@Transactional
@WebAppConfiguration
public class FreeMarkerEscapeByDefaultIntegrationTest
{
	@Inject
	private FreeMarkerTestRenderer renderer;

	@Test
	public void testVariablesRenderedInTemplateAreHtmlEscapedByDefault()
	{
		String message = "<script>TESTING 1 2 3</script>";
		String escapedMessage = "&lt;script&gt;TESTING 1 2 3&lt;/script&gt;";

		Map<String, Object> model = createModel(message);
		String rendered = renderer.processTemplateIntoString("test/escape_by_default.ftl", model);
		assertThat(
			"Rendered string should contain escaped text",
			rendered,
			containsString(escapedMessage));
		assertThat(
			"Rendered string should not contain unescaped text",
			rendered,
			not(containsString(message)));
	}

	private Map<String, Object> createModel(String message)
	{
		final HashMap<String, Object> model = new HashMap<>();
		model.put("message", message);
		return model;
	}
}
