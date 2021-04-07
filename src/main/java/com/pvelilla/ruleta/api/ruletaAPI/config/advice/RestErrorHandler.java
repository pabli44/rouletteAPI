package com.pvelilla.ruleta.api.ruletaAPI.config.advice;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.pvelilla.ruleta.api.ruletaAPI.config.exceptions.RecordNotFoundException;


@ControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ErrorMessage> handlerErrorException(RecordNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), request.getDescription(true)),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handlerErrorException(MethodArgumentNotValidException ex, WebRequest request) {
		final List<String> errors = new ArrayList<>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		return new ResponseEntity<>(new ErrorMessage(errors.toString(), request.getDescription(true)),
				HttpStatus.BAD_REQUEST);
	}

}
