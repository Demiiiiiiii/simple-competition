/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.io.Serializable;

public class AutoNumbersEntry extends NumbersEntry implements Serializable{
    private final int NUMBER_COUNT = 7;
    private final int MAX_NUMBER = 35;
	
    public int[] createNumbers (int seed) {
        ArrayList<Integer> validList = new ArrayList<Integer>();
	    int[] tempNumbers = new int[NUMBER_COUNT];
        for (int i = 1; i <= MAX_NUMBER; i++) {
    	    validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < NUMBER_COUNT; i++) {
    	    tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }
    //constructor for test mode
    public AutoNumbersEntry(int id,int seed){
        super(id);
        super.setNumbersInt(this.createNumbers(seed));

    }
    // constructor for normal mode
     public AutoNumbersEntry(int id){
        super(id);
        Random rand=new Random();
        super.setNumbersInt(this.createNumbers(rand.nextInt(100)));

    }
}
