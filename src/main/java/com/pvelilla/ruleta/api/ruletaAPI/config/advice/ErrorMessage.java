package com.pvelilla.ruleta.api.ruletaAPI.config.advice;

import java.time.LocalDateTime;

public class ErrorMessage {
	private LocalDateTime date;
	private String message;
	private String description;

	public ErrorMessage() {
	}

	public ErrorMessage(String message) {
		this.date = LocalDateTime.now();
		this.message = message;
	}

	public ErrorMessage(String message, String description) {
		this.date = LocalDateTime.now();
		this.message = message;
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
