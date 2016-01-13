package com.bright.appstarter.freemarker;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class FreemarkerOptionsCreator
{
	/**
	 * Create an options map suitable for use by <@spring.formSingleSelect/>
	 */
	public <T> LinkedHashMap<String, String> freemarkerOptions(
		List<T> choices,
		Function<T, String> valueMapper,
		Function<T, String> textMapper
		)
	{
		LinkedHashMap<String, String> options = new LinkedHashMap<>();
		options.put("", "- please select -");
		choices.stream().forEach(
			choice -> {
				options.put(valueMapper.apply(choice),
							textMapper.apply(choice));
			});
		return options;
	}
}
