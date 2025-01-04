package com.javaegitimleri.petclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaegitimleri.petclinic.service.EmailService;

@RestController
public class EmailController {
	
	@Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(
        @RequestParam String from,
        @RequestParam String to,
        @RequestParam String subject,
        @RequestParam String body
    ) {
        emailService.sendEmail(from,to, subject, body);
        return "Email sent successfully";
    }

}
