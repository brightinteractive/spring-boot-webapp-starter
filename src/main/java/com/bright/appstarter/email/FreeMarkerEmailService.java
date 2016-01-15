package com.bright.appstarter.email;

import javax.inject.Inject;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class FreeMarkerEmailService implements EmailService
{
	@Inject
	private MailSender mailSender;

	@Inject
	private EmailConfiguration emailConfiguration;

	@Inject
	private FreeMarkerRenderer freeMarkerRenderer;

	@Override
	public void send(String to, String from, String subject, String text)
	{
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(to);
		simpleMessage.setFrom(from);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(text);
		mailSender.send(simpleMessage);
	}

	@Override
	public void send(String to, String from, String templateName, EmailVariables variables)
	{
		String body = render(EmailService.BODY_TEMPLATE_PATH + templateName, variables.getBodyMap());
		String subject = render(EmailService.SUBJECT_TEMPLATE_PATH + templateName,
			variables.getSubjectMap());
		SimpleMailMessage simpleMessage = compose(to, from, body, subject);
		mailSender.send(simpleMessage);
	}

	private String render(String templateName, Object model)
	{
		return freeMarkerRenderer.render(templateName, model);
	}

	private SimpleMailMessage compose(String to, String from, String body, String subject)
	{
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(to);
		simpleMessage.setFrom(from);
		simpleMessage.setText(body);
		simpleMessage.setSubject(subject);
		return simpleMessage;
	}

	@Override
	public void send(String to, String templateName, EmailVariables variables)
	{
		send(to, emailConfiguration.getFromAddress(), templateName, variables);
	}

}
