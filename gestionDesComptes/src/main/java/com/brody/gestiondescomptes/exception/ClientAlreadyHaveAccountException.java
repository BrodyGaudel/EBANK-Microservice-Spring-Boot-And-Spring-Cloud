package com.brody.gestiondescomptes.exception;

public class ClientAlreadyHaveAccountException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ClientAlreadyHaveAccountException(String message) {
		super(message);
	}

}
