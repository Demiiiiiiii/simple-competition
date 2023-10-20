/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */


public class DataAccessException extends Exception {
	//constructor
    public DataAccessException() {
		super("The file was invalid.");
	}
	//constructor
	public DataAccessException( String message) {
		super(message);
	}

}
