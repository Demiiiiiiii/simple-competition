/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */


public class DataFormatException extends Exception {
	//constructor
    public DataFormatException() {
		super("The format was invalid.");
	}
	//constructor
	public DataFormatException( String message) {
		super(message);
	}

}
