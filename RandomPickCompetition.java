/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;
import java.util.Collections;

public class RandomPickCompetition extends Competition implements Serializable{
    private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};
	private ArrayList<Entry> entries=new ArrayList<Entry>();
    private ArrayList<Entry> winningEntries=new ArrayList<Entry>();
    private final int MAX_WINNING_ENTRIES = 3;
    
    //setter
    public void setWinningEntries(ArrayList<Entry> list){
        winningEntries=list;
    }
    public void setEntryList(ArrayList<Entry> list){
        entries=list;
    }    
    //getter
    public ArrayList<Entry> getEntryList(){
        return entries;
    }
    public ArrayList<Entry> getWinningEntries(){
        return winningEntries;
    }
    public int getId(){
        return super.getId();
    }
	
    public void drawWinners(){

    }
    /**
    *@param dp record the bills and members
    *draw winners from a RandomPickCompetition
    */
    public void drawWinners(DataProvider dp) {
        // no entry has been entered
        if(this.getEntryList().size()==0){
            System.out.println("The current competition has no entries yet!");
        }
        // entry exists
        else{
            Random randomGenerator = null;
            // test mode
            if (this.getIsTestingMode()) {
                randomGenerator = new Random(this.getId());
            }
            //normal mode 
            else {
                randomGenerator = new Random();
            }
            int winningEntryCount = 0;
            boolean repeat=false;// whether the member have repeated prizes
            ArrayList<Entry> winningEntries=new ArrayList<Entry>();
            while (winningEntryCount < MAX_WINNING_ENTRIES) {
                int winningEntryIndex = randomGenerator.nextInt(entries.size());        
                Entry winningEntry = entries.get(winningEntryIndex);                
                /*
                * Ensure that once an entry has been selected,
                * it will not be selected again.
                */
                if (winningEntry.getPrize() == 0) {
                    int currentPrize = prizes[winningEntryCount];
                    winningEntry.setPrize(currentPrize);
                    winningEntryCount++;
                }
            
                /*
                *ensure that one customer gets at most one winning entry.
                *if the same member got two or more prizes, record the highest one
                */                
                Bill bill=dp.getBill(winningEntry.getBillId());
                String memberId1=bill.getMemberId();// current memberId
                for(int i=0;i<winningEntries.size();i++){
                    repeat=false;
                    bill=dp.getBill(winningEntries.get(i).getBillId());
                    String memberId2=bill.getMemberId();// existing winner's memberId
                        // repeated member one
                        if(memberId1.equals(memberId2)){
                                repeat=true;
                                // the same member, choose the larger prize one 
                                if(winningEntry.getPrize()>winningEntries.get(i).getPrize()){
                                        winningEntries.set(i,winningEntry);
                                }
                        }
                }
                // no repeat members
                if(!repeat){
                    winningEntries.add(winningEntry);
                }
            }
            this.setState("completed");
            System.out.printf("Competition ID: %d, Competition Name: %s, Type: %s",this.getId(),this.getName(),this.getType());
            System.out.println();
            System.out.println("Winning entries:");
            // sort the entries
            Collections.sort(winningEntries);
            for(Entry entry:winningEntries){
                    Bill bill=dp.getBill(entry.getBillId());
                    Member member=dp.getMember(bill.getMemberId());
                    System.out.println("Member ID: "+member.getId()+", Member Name: "+member.getName()+", Entry ID: "+entry.getId()+", Prize: "+entry.getPrizeString());
                    
            }
            this.setWinningEntries(winningEntries);

        }        
    }
    
    /**
    *@param billId input of bill ID
    *@return true if the adding succeeded
    *check the correctness of billId, then output entries if correct
    */
    public boolean addEntries(DataProvider dp, String billId){
            int number;
            boolean success=false;
            if(billId.equals("")||billId.length()!=6||!isNumeric(billId)){
                System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
                System.out.println("Bill ID: ");                                
            }
            else{
                boolean exist=false;// whether billId exist in the bills.csv
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
                            if(entries==null){
                                initial=0;
                            }
                            else{
                                initial=entries.size();// length of last entryList
                                if(entries==null){
                                    initial=0;
                                }
                                else{
                                    initial=entries.size();
                                }
                                int id=1+initial;// entry id
                                System.out.printf("This bill ($%s) is eligible for %d entries.",bill.getAmount(),number);
                                System.out.println();
                                System.out.println("The following entries have been automatically generated:");
                                for(int i=0;i<number;i++){
                                        Entry entry=new Entry(id);
                                        entry.setBillId(billId);
                                        bill.setUsed("true");
                                        entries.add(entry);
                                        id++;
                                }
                                this.setEntryList(entries);
                                success=true;
                                for(int i=initial;i<id-1;i++){
                                        System.out.printf("Entry ID: %s",entries.get(i).getIdString());
                                        System.out.println();
                                }
                                
                            }
                        }
                            
                    }
                }
                else{
                    System.out.println("This bill does not exist. Please try again.");
                    System.out.println("Bill ID: ");
                } 
            }return success;
    }
        
   public void addEntries(){

   }
  
    public boolean getIsTestingMode(){
        boolean t=true;
           if(this.getMode().equals("T"))
                t=true;
            else if(this.getMode().equals("N"))
                t=false;
        return t;
    }
   public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){   
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
