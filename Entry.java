/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.io.Serializable;

// implement Comparable for sorting entries
public class Entry implements Serializable,Comparable<Entry>{
    private int entryId;
    private String billId;
    private int prize;
    
    //getter
    public int getPrize(){
          return prize;
    }
    public String getBillId(){
          return billId;
    }
    // for test format
    public String getPrizeString(){
        String show=null;
        if(prize<10){
            show=prize+"    ";
        }
        else if(prize<100&&prize>9){
            show=prize+"   ";
        }
        else if(prize>99&&prize<1000){
            show=prize+"  ";
        }
        else if(prize>999&&prize<10000){
            show=prize+" ";
        }
        else{
            show=prize+"";
        }
        return show;
    }
    public int getId(){
          return entryId;
    }
    //for test format
    public String getIdString(){
        String show=null;
        if(entryId<10){
            show=entryId+"     ";
        }
        else if(entryId>9&&entryId<100){
            show=entryId+"    ";
        }
        else if(entryId>99&&entryId<1000){
            show=entryId+"   ";
        }
          return show;
    }
    //setter
    public void setPrize(int n){
        prize=n;
    }
    public void setId(int n){
        entryId=n;
    }
    public void setBillId(String n){
        billId=n;
    }

    //constructor
    public Entry(int id){
        entryId=id;
    }
    //constructor
    public Entry(){

    }
    @Override 
    // compare entries by entry ID ascending
    public int compareTo(Entry entry){
        return (this.getId()>entry.getId()?1:(this.getId()==entry.getId()?0:-1));
    }
}
