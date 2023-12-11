package com.safesmart.safesmart.common;

public class CommonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommonException(String mesage) {
		super(mesage);
	}

	public static CommonException CreateException(CommonExceptionMessage message, Object... paramaters) {
		String format = message.getMessgae();
		return new CommonException(String.format(format, paramaters));
	}

}
