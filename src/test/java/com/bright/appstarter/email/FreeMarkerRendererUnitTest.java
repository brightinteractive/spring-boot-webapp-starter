package com.bright.appstarter.email;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.bright.appstarter.email.FreeMarkerRenderer;

import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class FreeMarkerRendererUnitTest
{
	@Mock
	private Configuration configuration;

	@InjectMocks
	private FreeMarkerRenderer freeMarkerRenderer;

	@Test
	public void testRenderCallsTemplateWithCorrectMap() throws Exception
	{
		String templateName = "template";
		Template template = mock(Template.class);
		when(configuration.getTemplate(templateName + FreeMarkerRenderer.FTL_EXTENSION)).thenReturn(template);
		Map<String, String> map = ImmutableMap.<String, String>builder().put("name", "Martin").build();
		String text = "some text containing map values";
		templateProcessWithMapShouldOutput(template, map, text);

		String output = freeMarkerRenderer.render(templateName, map);

		assertEquals(text, output);
	}

	/*
	 *  Given the correct map input, this sets up the passed in string writer to produce the specified text.
	 */
	private void templateProcessWithMapShouldOutput(Template template, Map<String, String> map,
		String result) throws TemplateException, IOException
	{
		doAnswer(new Answer<Object>()
		{
			public Object answer(InvocationOnMock invocation)
			{
				Object[] args = invocation.getArguments();
				StringWriter stringWriter = (StringWriter) args[1];
				stringWriter.append(result);
				return null;
			}
		}).when(template).process(eq(map), any(StringWriter.class));
	}
}
