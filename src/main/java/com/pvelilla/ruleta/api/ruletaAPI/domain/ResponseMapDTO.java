package com.pvelilla.ruleta.api.ruletaAPI.domain;

import java.util.HashMap;

public class ResponseMapDTO extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ResponseMapDTO() {
	}

	public ResponseMapDTO(String key, Object value) {
		this.putAttribute(key, value);
	}

	public ResponseMapDTO putAttribute(String key, Object value) {
		put(key, value);
		return this;
	}

}
