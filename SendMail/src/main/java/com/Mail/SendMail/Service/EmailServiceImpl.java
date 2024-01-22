package com.Mail.SendMail.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Mail.SendMail.Model.Email;

import jakarta.mail.MessagingException;

/**
 * SimpleMailMessage-Models a simple mail message, including data such as the
 * from, to, cc, subject,and text fields. Consider JavaMailSender and JavaMail
 * MimeMessages for creatingmore sophisticated messages, for example messages
 * with attachments, specialcharacter encodings, or personal names that
 * accompany mail addresses.
 */
@Service
public class EmailServiceImpl implements EmailService {

	/*
	 * @Value("${email.set.bcc}") private String bcc;
	 */

	@Autowired
	private JavaMailSender sender;

	/**
	 * SendEmail method is used to send Simple Mail
	 */
	public void sendEmail(Email email) throws MessagingException {
		SimpleMailMessage simplemailmessage = new SimpleMailMessage();
		simplemailmessage.setTo(email.getTo());
		simplemailmessage.setText(email.getMessage());
		simplemailmessage.setSubject(email.getSubject());
		sender.send(simplemailmessage);

	}

	/**
	 * sendEmailAttachments method is used to send Mail with Attachments
	 */
	public void sendEmailAttachments(Email email) {
		jakarta.mail.internet.MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			helper.setText(email.getMessage());
			helper.addAttachment("File.png", new File(".\\src\\main\\resources\\File.png")); // replace with file path
			sender.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * -sendCustomEmailTemp method is used to send Mail with custom Email templates.
	 * -Files.readAllBytes(Path path) − This method is used to reads all the bytes from the file at given path 
	 * and returns the byte array containing the bytes read from the file.
	 * -Creating an absolute path is done by calling the Paths.get() factory method with the absolute file as parameter. 
	 *  Here is an example of creating a Path instance: Path path = Paths.get("c:\\data\\myfile.txt");
	 */
	 public void sendCustomEmailTemp(Email email) {
		jakarta.mail.internet.MimeMessage message = sender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setTo(email.getTo());
			helper.setSubject(email.getSubject());
			helper.setText(email.getMessage());
			
			String templatePath = ".\\src\\main\\resources\\templates\\template.html";
			String htmlContent = new String(Files.readAllBytes(Paths.get(templatePath)), StandardCharsets.UTF_8);
			
			htmlContent = htmlContent.replace("[NAME]", email.getTo()); // Replacing placeholders with dynamic data
			htmlContent = htmlContent.replace("[MESSAGE]", email.getMessage());
			htmlContent = htmlContent.replace("[CONTACT_EMAIL]", "test@gmail.com");
			helper.setText(htmlContent, true); // Set template as email content
   
			sender.send(message);

		} catch (IOException | MessagingException e) {
			e.getStackTrace();
		}
	}

}
