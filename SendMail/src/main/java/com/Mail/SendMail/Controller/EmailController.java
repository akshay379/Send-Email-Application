package com.Mail.SendMail.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Mail.SendMail.Model.Email;
import com.Mail.SendMail.Service.EmailServiceImpl;

@RestController
public class EmailController {

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmail(@RequestBody Email email) {
		try {
			emailServiceImpl.sendEmail(email);
			return ResponseEntity.ok("Email Sent Successfully!");
		} catch (Exception ex) {
			return ResponseEntity.status(500).body("Error in sending email: " + ex.getMessage());
		}
	}

	@PostMapping("/sendEmailWithAttachment")
	public ResponseEntity<String> sendEmailAttachment(@RequestBody Email email) {
		try {
			emailServiceImpl.sendEmailAttachments(email);
			return ResponseEntity.ok("Email Sent Successfully with Attachments!");
		} catch (Exception ex) {
			return ResponseEntity.status(500).body("Error in sending email with Attachments: " + ex.getMessage());
		}
	}
	
	@PostMapping("/sendCustomEmail")
	public ResponseEntity<String> sendCustomEmailTemp(@RequestBody Email email) {
		try {
			emailServiceImpl.sendCustomEmailTemp(email);
			return ResponseEntity.ok("Email Sent Successfully with Custom Template!");
		} catch (Exception ex) {
			return ResponseEntity.status(500).body("Error in sending email with Custom Template: " + ex.getMessage());
		}
	}

	@GetMapping("/hello/{name}/{age}")
	public String insert(@PathVariable("name") String name, @PathVariable("age") int age) {

		// Print and display name and age
		return name + " " + age;
	}

}