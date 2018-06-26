package com.codeboyq.AdminiMover;

public class AdminiMoverException extends Exception {
	
	private static final long serialVersionUID = -4353093866899178794L;

	public AdminiMoverException (String message) {
        super (message);
    }

    public AdminiMoverException (Throwable cause) {
        super (cause);
    }

    public AdminiMoverException (String message, Throwable cause) {
        super (message, cause);
    }

}
