package com.javaegitimleri.petclinic.service;

public interface EmailService {
	public void sendEmail(String from, String to, String subject, String body);
}