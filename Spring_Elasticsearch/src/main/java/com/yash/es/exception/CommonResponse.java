package com.yash.es.exception;

public class CommonResponse extends Exception{

	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	
	public CommonResponse() {
		super();
	}


	public CommonResponse(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
