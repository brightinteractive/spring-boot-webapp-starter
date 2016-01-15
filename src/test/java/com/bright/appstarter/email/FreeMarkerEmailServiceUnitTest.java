package com.bright.appstarter.email;

import static org.mockito.Mockito.*;

import java.util.Map;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FreeMarkerEmailServiceUnitTest
{
	@Mock
	private MailSender mailSender;

	@Mock
	private EmailConfiguration emailConfiguration;

	@Mock
	private FreeMarkerRenderer freeMarkerRenderer;

	@InjectMocks
	private FreeMarkerEmailService freeMarkerEmailService;

	@Test
	public void sendTemplatedEmailSendsEmail() throws Exception
	{
		String to = "martin@bright-interactive.co.uk";
		String from = "noreply@noreply.com";
		String subject = "A subject for Martin";
		String body = "Hi Martin, here is your email";
		String templateName = "template";
		Map<String, Object> bodyMap = ImmutableMap.<String, Object> builder().put("name", "Martin")
			.build();
		when(freeMarkerRenderer.render(EmailService.BODY_TEMPLATE_PATH + templateName, bodyMap))
			.thenReturn(body);
		Map<String, Object> subjectMap = ImmutableMap.<String, Object> builder()
			.put("name", "Martin").build();
		when(
			freeMarkerRenderer
				.render(EmailService.SUBJECT_TEMPLATE_PATH + templateName, subjectMap)).thenReturn(
			subject);

		EmailVariables variables = new ImmutableEmailVariablesImpl(subjectMap, bodyMap);

		freeMarkerEmailService.send(to, from, templateName, variables);

		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(to);
		simpleMessage.setFrom(from);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(body);
		verify(mailSender).send(simpleMessage);
	}

	@Test
	public void sendTemplatedEmailUsesDefaultFromAddress() throws Exception
	{
		String to = "martin@bright-interactive.co.uk";
		String from = "noreply@noreply.com";
		when(emailConfiguration.getFromAddress()).thenReturn(from);
		String subject = "A subject";
		String body = "Hi Martin, here is your email";
		String templateName = "template";
		Map<String, Object> bodyMap = ImmutableMap.<String, Object> builder().put("name", "Martin")
			.build();
		when(freeMarkerRenderer.render(EmailService.BODY_TEMPLATE_PATH + templateName, bodyMap))
			.thenReturn(body);
		Map<String, Object> subjectMap = ImmutableMap.of();
		when(
			freeMarkerRenderer
				.render(EmailService.SUBJECT_TEMPLATE_PATH + templateName, subjectMap)).thenReturn(
			subject);

		EmailVariables variables = new ImmutableEmailVariablesImpl(subjectMap, bodyMap);
		freeMarkerEmailService.send(to, templateName, variables);

		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(to);
		simpleMessage.setFrom(from);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(body);
		verify(mailSender).send(simpleMessage);
	}

	@Test
	public void sendEmailSendsEmail()
	{
		String to = "martin@bright-interactive.co.uk";
		String from = "noreply@noreply.com";
		String subject = "A subject";
		String text = "Hi Martin, here is your email";

		freeMarkerEmailService.send(to, from, subject, text);

		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(to);
		simpleMessage.setFrom(from);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(text);
		verify(mailSender).send(simpleMessage);
	}

}
