/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;
import java.util.Collections;

public class LuckyNumbersCompetition extends Competition implements Serializable{
    private ArrayList<NumbersEntry> entryList=new ArrayList<NumbersEntry>();
    private ArrayList<NumbersEntry> winningEntries=new ArrayList<NumbersEntry>();

   //setter
    public void setWinningEntries(ArrayList<NumbersEntry> list){
        winningEntries=list;
    }
    public void setEntryList(ArrayList<NumbersEntry> list){
        entryList=list;
    }
    //getter
    public ArrayList<NumbersEntry> getEntryList(){
        return entryList;
    }
    public ArrayList<NumbersEntry> getWinningEntries(){
        return winningEntries;
    }
    
    public void addEntries(){
    }
    /*
    *@param billId input of bill ID
    *this method is used to check if billId is correct input
    *@return true for correct input
    */  
    public boolean addEntries(DataProvider dp, String billId){  
        int number;
        boolean success=false;
        if(billId.equals("")||billId.length()!=6||!isNumeric(billId)){
            System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
            System.out.println("Bill ID: ");                                
        }
        else{
            boolean exist=false;
            for(Bill bill:dp.getBillList()){
                // the biiId exists
                if(bill.getBillId().equals(billId)){
                    exist=true;
                }
            }
            if(exist){
                number=(int)(Float.parseFloat(dp.getBill(billId).getAmount())/50);           
                Bill bill=dp.getBill(billId);
                if(bill.getUsed().equals("true"))
                {   
                    System.out.println("This bill has already been used for a competition. Please try again.");
                    System.out.println("Bill ID: ");
                }
                else if(bill.getUsed().equals("false"))
                {
                    if(bill.getMemberId().equals("")){
                        System.out.println("This bill has no member id. Please try again.");
                        System.out.println("Bill ID: ");
                    }
                    else{
                        int initial;
                        if(entryList==null){
                            initial=0;
                        }
                        else{
                            initial=entryList.size();// length of last entryList
                        }
                        System.out.printf("This bill ($%s) is eligible for %d entries.",bill.getAmount(),number);
                        success=true;
                    }
                }
        }
        else{
            System.out.println("This bill does not exist. Please try again.");
            System.out.println("Bill ID: ");
        }
        } return success;
    }
        
     
  /*
    *@param billId input of bill ID
    *@param line input of manual entry content
    *@param number the total number of entries
    *@param manualNumber the number of manual entries
    *@param initial the length of initial entryList
    *check if line is correct input and output entries if correct
    *@return y value
    */  
  public int manualAddEntry(DataProvider dp,String line,String billId,int number,int manualNumber,int initial){
            String[] chunks=line.split("\\s+");
            int y=1;
            if(chunks.length<7){
                y=2;
            }
            else if(chunks.length>7){
                y=3;
            }
            // 7 input
            else {
                for(int i=0;i<chunks.length;i++){
                    // not a number in it
                    if(!isNumeric(chunks[i])){
                        y=4;
                        break;
                    }
                    // not in the right range
                    else if(Integer.parseInt(chunks[i])<1||Integer.parseInt(chunks[i])>35){
                        y=5;
                        break;
                    }
                    for(int k=i+1;k<chunks.length;k++){
                        if(chunks[i].equals(chunks[k])){
                            y=6;
                            break;
                        }
                  }
                }
            }
            // manual input is 0
            if(line.equals("0")){
                y=0;
            }
            int d=entryList.size()+1;
            switch(y){               
                case 1:
                    // sort input in ascending way
                    int[] int_chunks = toIntArray(chunks);
                    Arrays.sort(int_chunks);
                    String[] sorted_chunks=new String[chunks.length];
                    for(int i=0;i<chunks.length;i++){
                       sorted_chunks[i]=String.valueOf(int_chunks[i]);
                    }                  
                    NumbersEntry entry=new NumbersEntry(d,sorted_chunks);  
                    entry.setBillId(billId);                 
                    Bill bill=dp.getBill(billId);
                    bill.setUsed("true");
                    entryList.add(entry);
                    if(entryList.size()==initial+manualNumber){
                    y=0;
                    }break;
                case 2:
                    System.out.println("Invalid input! Fewer than 7 numbers are provided. Please try again!");
                    break;
                case 3:
                    System.out.println("Invalid input! More than 7 numbers are provided. Please try again!");
                    break;
                case 4:
                    System.out.println("Invalid input! Numbers are expected. Please try again!");
                    break;
                case 5:
                    System.out.println("Invalid input! All numbers must be in the range from 1 to 35!");
                    break;
                case 6:
                    System.out.println("Invalid input! All numbers must be different!");
                    break;                   
            }
             if(y==0){                
                int id=1+initial+manualNumber;// entry ID
                // auto-generated entries
                for(int i=initial+manualNumber;i<initial+number;i++){
                    // test mode
                    if(this.getIsTestingMode()){ 
                        AutoNumbersEntry entry=new AutoNumbersEntry(id,id-1); 
                        entry.setBillId(billId);
                        entryList.add(entry);
                        id++;
                    }
                    // normal mode
                    else{
                       AutoNumbersEntry entry=new AutoNumbersEntry(id);
                       entry.setBillId(billId);
                       entryList.add(entry);
                       id++; 
                    }                     
                }
                Bill bill=dp.getBill(billId);
                bill.setUsed("true");
                System.out.println("The following entries have been added:");
                for(int i=initial;i<initial+number;i++){
                    if(entryList.get(i).getId()<10){
                        System.out.printf("Entry ID: %d      Numbers:%s",entryList.get(i).getId(),entryList.get(i).getNumbers());
                        System.out.println();
                    }
                    else{
                        System.out.printf("Entry ID: %d     Numbers:%s",entryList.get(i).getId(),entryList.get(i).getNumbers());
                         System.out.println();
                    }
                }  
             }        
            return y;
        }
   

   public void drawWinners(){
   }
   /**
    *@param sum used for recording the number of same digits
    */  
    public void drawWinners(DataProvider dp){
        // if no entry has been entered
        if(this.getEntryList().size()==0){
            System.out.println("The current competition has no entries yet!");
        }
        else{
             AutoNumbersEntry entrySample;
            // test mode
            if(this.getIsTestingMode())
            {
               entrySample=new AutoNumbersEntry(entryList.size()+1,this.getId());
            }
            // normal mode
            else{
               entrySample=new AutoNumbersEntry(entryList.size()+1);
            }
            for(NumbersEntry entry: entryList)
            {
                int sum=0;
                for(int i=0;i<7;i++)
                {
                    for(int j=0;j<7;j++)
                    {
                        if(entry.getIntNumbers()==null)
                        {
                            String[] list=entry.getStringNumbers();
                            if(Integer.parseInt(list[i])==entrySample.getIntNumbers()[j])
                            {
                                sum++;
                            }
                        }
                        else
                        {
                            if(entry.getIntNumbers()[i]==entrySample.getIntNumbers()[j])
                            {
                                sum++;
                            }
                        }
                    }
                }
                switch(sum){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        entry.setPrize(50);
                        break;
                    case 3:
                        entry.setPrize(100);
                        break;
                    case 4:
                        entry.setPrize(500);
                        break;
                    case 5:
                        entry.setPrize(1000);
                        break;
                    case 6:
                        entry.setPrize(5000);
                        break;
                    case 7:
                        entry.setPrize(50000);
                        break;
                 }
                }
                this.setState("completed");
                System.out.printf("Competition ID: %d, Competition Name: %s, Type: %s",this.getId(),this.getName(),this.getType());
                System.out.println();
                System.out.println("Lucky Numbers:"+entrySample.getNumbers());
                System.out.println("Winning entries:");
                 /*
                *ensure that one customer gets at most one winning entry.
                *if the same member got more prizes, record the highest one with the smallest enry ID
                */  
                ArrayList<NumbersEntry> winningEntries=new ArrayList<NumbersEntry>();
                boolean repeat=false;
                for(NumbersEntry entry:entryList)
                {
                    if(entry.getPrize()!=0)
                    {  
                        Bill bill=dp.getBill(entry.getBillId());
                        String memberId1=bill.getMemberId();
                        for(int i=0;i<winningEntries.size();i++)
                        {   
                            repeat=false;
                            bill=dp.getBill(winningEntries.get(i).getBillId());
                            String memberId2=bill.getMemberId();
                            // repeated member one
                            if(memberId1.equals(memberId2))
                            {
                                repeat=true;
                                // the same member, choose the larger prize one 
                                if(entry.getPrize()>winningEntries.get(i).getPrize())
                                {
                                            winningEntries.set(i,entry);
                                }
                                // the same prize, choose the smaller entryId one
                                else if(entry.getPrize()==winningEntries.get(i).getPrize())
                                {
                                    if(entry.getId()<winningEntries.get(i).getId())
                                    {
                                        winningEntries.set(i,entry);
                                    }
                                }
                            }
                        }
                        // no repeated member one
                        if(!repeat)
                        {
                            winningEntries.add(entry);
                        }
                    }
                }
                Collections.sort(winningEntries);
                for(NumbersEntry entry:winningEntries)
                {
                            Bill bill=dp.getBill(entry.getBillId());
                            Member member=dp.getMember(bill.getMemberId());
                            System.out.printf("Member ID: %s, Member Name: %s, Prize: %s",member.getId(),member.getName(),entry.getPrizeString());
                            System.out.println();
                            System.out.println("--> Entry ID: "+entry.getId()+", Numbers:"+entry.getNumbers());
                }
                this.setWinningEntries(winningEntries);
            }
  
    }
    
    
    public boolean getIsTestingMode(){
        boolean t=true;
           if(this.getMode().equals("T"))
                t=true;
            else if(this.getMode().equals("N"))
                t=false;
        return t;
    }

    public boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){   
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

   // convert a string array to a int array
    public int[] toIntArray(String[] str_arr) {
        int[] int_arr = new int[str_arr.length] ;
        for (int i = 0; i< int_arr.length; i++) {
            int_arr[i] = Integer.parseInt(str_arr[i]);
        }
        return int_arr;
    }


}





