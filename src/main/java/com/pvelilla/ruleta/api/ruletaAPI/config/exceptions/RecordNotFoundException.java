package com.pvelilla.ruleta.api.ruletaAPI.config.exceptions;

public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String message) {
		super(message);
	}

	public RecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RecordNotFoundException(String objectName, Object id) {
		super(new StringBuilder(objectName).append(" not found by id [").append(id).append("]").toString());
	}

}
