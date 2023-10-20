/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.io.Serializable;

public class NumbersEntry extends Entry implements Serializable{
    private String[] numbers;
    private int[] numbersInt;

    //getter
    public String getNumbers(){
        String num="";
        if(numbers!=null){
            for(String n:numbers){
                if(Integer.parseInt(n)<10){
                    num=num+"  "+n;
                }
                else{
                    num=num+" "+n;
                }
            }
        }
        else{
            for(int n:numbersInt){
                if(n<10){
                    num=num+"  "+n;
                }
                else{
                    num=num+" "+n;
                }
            }
            num=num+" "+"[Auto]";
        }
        return num;
    }

    public int[] getIntNumbers(){
        return numbersInt;
    }
    public String[] getStringNumbers(){
        return numbers;
    }
    //setter
     public void setNumbersInt(int[] list){
        numbersInt=list;
    }
    public void setNumbers(String[] list){
        numbers=list;
    }
    
   // constructor
    public NumbersEntry(int id, String[] list){
        super(id);
        numbers=list;

    }
     // constructor
    public NumbersEntry(int id, int[] list){
        super(id);
        numbersInt=list;

    }
    // constructor
    public NumbersEntry(int id){
        super(id);
    }
    
}
