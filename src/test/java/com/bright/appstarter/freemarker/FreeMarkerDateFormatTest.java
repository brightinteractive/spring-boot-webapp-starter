package com.bright.appstarter.freemarker;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;
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
public class FreeMarkerDateFormatTest
{
	private static final String ARBITRARY_BUT_FIXED_DATE_IN_FORMAT_REQUIRED_BY_DATE_PICKER = "04/Nov/2015";

	@Inject
	private FreeMarkerTestRenderer renderer;

	@Test
	public void testDatesAreRenderedInCorrectFormat()
	{
		Map<String, Object> model = createModel();
		String rendered = renderer.processTemplateIntoString("test/date_format.ftl", model);
		assertThat(
			rendered,
			containsString(ARBITRARY_BUT_FIXED_DATE_IN_FORMAT_REQUIRED_BY_DATE_PICKER));
	}

	private Map<String, Object> createModel()
	{
		final HashMap<String, Object> model = new HashMap<>();
		model.put("date", arbitraryButFixedDate());
		return model;
	}

	private Date arbitraryButFixedDate()
	{
		return Date.from(Instant.parse("2015-11-04T12:30:32.65Z"));
	}
}
