package com.helpchat.tests.utilities;

public class AkoshaException  extends java.lang.Exception {

	private static final long serialVersionUID = 1L;
	
	public AkoshaException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public AkoshaException(Throwable cause)
	{
		super(cause);
	}
	
	public AkoshaException(String message)
	{
		super(message);
	}
}
