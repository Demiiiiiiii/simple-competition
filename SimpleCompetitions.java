/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.File;
import java.io.Serializable;


public class SimpleCompetitions implements Serializable{
    private static ArrayList<Competition> competitionList=new ArrayList<Competition>();
    private String mode; 
    
/**
*@param j type of new competiton
*@param name name of new competition 
*@param mode test or normal
*@return the new created competition
*/
    public Competition addNewCompetition(String j, String name,String mode) {
        Competition competition=null;
        if(j.equals("L")||j.equals("l")){
            LuckyNumbersCompetition c=new LuckyNumbersCompetition();
            c.setType("LuckyNumbersCompetition");
            competition=c;
        }
            else {
            RandomPickCompetition c=new RandomPickCompetition();
            c.setType("RandomPickCompetition");
            competition=c;
            }
            competition.setId(competitionList.size()+1);
            competition.setName(name);
            competition.setState("active");
            competition.setMode(mode);
            competitionList.add(competition);
            System.out.println("A new competition has been created!");
            System.out.printf("Competition ID: %d, Competition Name: %s, Type: %s",competition.getId(),competition.getName(),competition.getType());
            System.out.println(); 
            return competition;              
        }
    

    public void report() {
        if(this.getCompetitionList().size()==0){
            System.out.println("No competition has been created yet!");
        }
        else{
        System.out.println("----SUMMARY REPORT----");
        int num1=0;// number of active competition
        int num2=0;// number of completed competition
        for(Competition c:competitionList){
            if(c.getState().equals("active")){
                num1++;
            }
            else if(c.getState().equals("completed")){
                num2++;
            }
        }
        System.out.println("+Number of completed competitions: "+num2);
        System.out.println("+Number of active competitions: "+num1);
        String active="";
        for(Competition competition:competitionList){
            System.out.println();
            if(competition.getState().equals("completed")){
                active="no";
            }
            else if(competition.getState().equals("active")){
                active="yes";
            }
            System.out.println("Competition ID: "+competition.getId()+", name: "+competition.getName()+", active: "+active);
            if(competition.getType().equals("LuckyNumbersCompetition")){
                LuckyNumbersCompetition ct=(LuckyNumbersCompetition)competition;
                System.out.println("Number of entries: "+ct.getEntryList().size());
                // for completed competition 
                if(competition.getState().equals("completed")){
                    System.out.println("Number of winning entries: "+ct.getWinningEntries().size());
                    int sum=0;
                    for(NumbersEntry entry:ct.getWinningEntries()){
                        sum=sum+entry.getPrize();
                    }
                    System.out.println("Total awarded prizes: "+sum);
                }
            }
            else if(competition.getType().equals("RandomPickCompetition")){
                RandomPickCompetition ct=(RandomPickCompetition)competition;
                System.out.println("Number of entries: "+ct.getEntryList().size());
                // for completed competition 
                if(competition.getState().equals("completed")){
                    System.out.println("Number of winning entries: "+ct.getWinningEntries().size());
                    int sum=0;
                    for(Entry entry:ct.getWinningEntries()){
                        sum=sum+entry.getPrize();
                    }
                    System.out.println("Total awarded prizes: "+sum);
               }
           }
    	
        }}
    }
    /**
    *@param filename name of loading competition 
    *@throws A DataAcessException will be thrown if the file cannot read/open.
    *@return the loaded competition arraylist
    */
	public ArrayList<Competition> loadCompetition(String filename) throws DataAccessException {
		ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(filename));
            ArrayList<Competition> competitionList=(ArrayList<Competition>)inputStream.readObject();
            inputStream.close();
            return competitionList;
        } catch (FileNotFoundException e) {
            throw new DataAccessException("Could not open file: " + filename);            
        } catch(IOException e) {
            throw new DataAccessException("Could not read file: " + filename);
        } catch(ClassNotFoundException e) {
                throw new DataAccessException("Class not found.");
        }
    }
    /**
    *@param filename name of saved competition 
    *@throws A DataAcessException will be thrown if the file cannot find/open.
    *@return the loaded competition arraylist
    */
    public void saveCompetition(String filename) throws DataAccessException{
        ObjectOutputStream outputStream = null;	
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            outputStream.writeObject(this.getCompetitionList());
            System.out.println("Competitions have been saved to file.");
        } catch(FileNotFoundException e){
            throw new DataAccessException("File mot find. ");
        } catch(IOException e){
            throw new DataAccessException("Error when saving file. ");
        } finally{
            try{
                outputStream.flush();
                outputStream.close();
            }catch(IOException e){
                throw new DataAccessException("Error when closing file. ");
            }
            
        }
    }    
    /**
    * Main program that uses the main SimpleCompetitions class
    * @param args main program arguments
    */
    public static void main(String[] args) {    	
    	//Create an object of the SimpleCompetitions class
        SimpleCompetitions sc = new SimpleCompetitions();
        DataProvider dp;
        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
        Scanner in= new Scanner(System.in);
        String j,m,b;
        int n;
        String mode=null;// whether the competition' mode, T for test, N for normal
        /**
        *loop for simplecompetition option
        *load competitions or start new competitions
        */
        while(true)
        {
            System.out.println("Load competitions from file? (Y/N)?");
            j=in.nextLine();
            // create new simplecompetition
            if(j.equals("N")||j.equals("n"))
            {  
                // loop for input of mode option    
              while(true){
                System.out.println("Which mode would you like to run? (Type T for Testing, and N for Normal mode):");
                j=in.nextLine();
                if(j.equals("N")||j.equals("n"))
                {   
                    mode="N";
                    break;
                }
                else if(j.equals("T")||j.equals("t")){
                    mode="T";
                    break;
                }
                else{
                    System.out.println("Invalid mode! Please choose again.");
                }
              }          
              break;
            }
            // load simplecompetition
            else if(j.equals("Y")||j.equals("y"))
            {   
                System.out.println("File name:");
                j=in.nextLine();
                try{
                    sc.setCompetitionList(sc.loadCompetition(j));
                     mode=sc.getCompetitionList().get(0).getMode();
                } catch(DataAccessException e){
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                break;
            }
            // invalid input
            else{
                System.out.println("Unsupported option. Please try again!");
            }
        }
        // load files of members and bills
        System.out.println("Member file: ");
        m=in.nextLine();
        System.out.println("Bill file: ");
        b=in.nextLine();
        /**
        *loop for main menu
        *catch DataAcessException and DataFormatException
        *@
        */
        try{
            dp = new DataProvider(m,b); 
            // main menu loop
            while(true){
                System.out.println("Please select an option. Type 5 to exit.");
                System.out.println("1. Create a new competition");
                System.out.println("2. Add new entries");
                System.out.println("3. Draw winners");
                System.out.println("4. Get a summary report");
                System.out.println("5. Exit");
                j=in.nextLine();
                // input 1
                if(j.equals("1")){
                    if(sc.existActiveCompetition()){
                        System.out.println("There is an active competition. SimpleCompetitions does not support concurrent competitions!");
                    }
                    else{                      
                        // loop for input of competition type
                        while(true){
                            System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
                            j=in.nextLine();
                            if(j.equals("L")||j.equals("R")||j.equals("r")||j.equals("l")){
                                System.out.println("Competition name: ");
                                m=in.nextLine();
                                sc.addNewCompetition(j,m,mode);
                                break;
                            }
                            else{
                                System.out.println("Invalid competition type! Please choose again.");
                            }
                        }
                    }
                }
                // input 2
                else if(j.equals("2")){
                    if(!sc.existActiveCompetition()){
                        System.out.println("There is no active competition. Please create one!");
                    }
                    else{
                        Competition competition;
                        for(Competition c:competitionList){
                            if(c.getState().equals("active")){
                                competition=c;                           
                                System.out.println("Bill ID: ");   
                                /**
                                *type of LuckyNumbersCompetition
                                *4 steps: 1.input bill ID.2.input manual entries number.3.input 7 numbers for manual entries.4.input options of continue or stop
                                *@param end for the whole process
                                *@param initial length of last(initial) entryList
                                *addEntries used in the 1 step
                                *manualAddEntry used in the 3 step
                                */                      
                                if(competition.getType().equals("LuckyNumbersCompetition")){
                                    LuckyNumbersCompetition ct=(LuckyNumbersCompetition)competition;
                                    boolean end=false;
                                    // whole loop of adding entries
                                    while(!end){
                                        int initial;
                                        // definition of initial 
                                        if(ct.getEntryList()==null){
                                            initial=0;
                                        }
                                        else{
                                            initial=ct.getEntryList().size();
                                        }
                                        // step 1
                                        boolean success=false;// whether input of bill ID is correct
                                        while(!success){
                                            j=in.nextLine();
                                            success=ct.addEntries(dp,j);
                                        }
                                        String line;
                                        String billId=j;
                                        int manualNumber;
                                        int number=(int)(Float.parseFloat(dp.getBill(billId).getAmount())/50);// number of entry numbers
                                        // step 2
                                        System.out.println(" How many manual entries did the customer fill up?: ");
                                        line=in.nextLine();
                                        while(true){                                       
                                            //correct input
                                            if(!line.equals("")&&isNumeric(line)&&-1<Integer.parseInt(line)&&Integer.parseInt(line)<1+number){
                                                manualNumber=Integer.parseInt(line);
                                                break;
                                            }
                                            else{
                                                System.out.printf("The number must be in the range from 0 to %d. Please try again.",number);
                                                System.out.println();
                                            }
                                            line=in.nextLine();
                                        }
                                        // step 3
                                        while(ct.getEntryList().size()<initial+manualNumber+1){
                                            if(!line.equals("0")){                                                                         
                                                System.out.println("Please enter 7 different numbers (from the range 1 to 35) separated by whitespace.");
                                                j=in.nextLine();
                                                ct.manualAddEntry(dp,j,billId,number,manualNumber,initial);
                                            }
                                            else{
                                                ct.manualAddEntry(dp,line,billId,number,manualNumber,initial);
                                            }
                                        }
                                        // step 4
                                        while(true){ 
                                            System.out.println("Add more entries (Y/N)?");
                                            j=in.nextLine();                       
                                            if(j.equals("N")||j.equals("n")||j.equals("Y")||j.equals("y")){
                                                break;
                                            }
                                            else{
                                                System.out.println("Unsupported option. Please try again!");
                                            }
                                        }
                                        if(j.equals("N")||j.equals("n")){
                                            end=true;
                                            break;
                                        }
                                        else if(j.equals("Y")||j.equals("y")){
                                            System.out.println("Bill ID: ");
                                            continue;
                                        }
                                 }
                    }                  
                    /**
                    *type of RandomPickCompetition
                    *2 steps: 1.input bill ID.2.input options of continue or stop
                    *@param end for the whole process
                    *@param initial length of last(initial) entryList
                    *addEntries used in the 1 step
                    */   
                    else if(competition.getType().equals("RandomPickCompetition")){
                            RandomPickCompetition ct=(RandomPickCompetition)competition;
                            boolean success=false;
                            boolean end=false;
                            // loop of adding entries
                            while(!end){
                                //step 1
                                success=false;
                                while(!success){
                                    j=in.nextLine();
                                    success=ct.addEntries(dp,j);
                                }
                                // step 2
                                while(true){ 
                                    System.out.println("Add more entries (Y/N)?");
                                    j=in.nextLine();                       
                                    if(j.equals("N")||j.equals("n")||j.equals("Y")||j.equals("y")){
                                        break;
                                    }
                                    else{
                                        System.out.println("Unsupported option. Please try again!");
                                    }
                                }
                                if(j.equals("N")||j.equals("n")){
                                    end=true;
                                    break;
                                }
                                else if(j.equals("Y")||j.equals("y")){
                                    System.out.println("Bill ID: ");
                                    end=false;
                                    continue;
                                }
                           } 
                    }                
                 }
                }
              } 
             } 
                // input 3
                else if(j.equals("3")){
                    if(!sc.existActiveCompetition()){
                        System.out.println("There is no active competition. Please create one!");
                    }
                    else{
                        Competition competition;
                        for(Competition c:competitionList){
                            if(c.getState().equals("active")){
                                competition=c;
                                if(competition.getType().equals("LuckyNumbersCompetition")){
                                    LuckyNumbersCompetition ct=(LuckyNumbersCompetition)competition;
                                        ct.drawWinners(dp);
                                }
                                else if(competition.getType().equals("RandomPickCompetition")){
                                    RandomPickCompetition ct=(RandomPickCompetition)competition;
                                    ct.drawWinners(dp);
                                }
                            }
                        }
                    }
                }
                // input 4
                else if(j.equals("4")){
                    sc.report();
                }
                // input 5
                else if(j.equals("5")){
                    System.out.println("Save competitions to file? (Y/N)?");
                    j=in.nextLine();
                    // loop for input of options of saving  
                    while(true){
                        if(j.equals("Y")||j.equals("y")){
                            // reminder of zero competition
                            if(sc.getCompetitionList().size()==0){
                                    System.out.println("No competition saved.");
                            }
                            System.out.println("File name:");
                            j=in.nextLine();	
                            sc.saveCompetition(j);
                            sc.updatBill(dp);
                            break;
                        }
                        else if(j.equals("N")||j.equals("n")){
                            break;
                        }
                        else{
                            System.out.println("Unsupported option. Please try again!");
                        }
                    }
                    System.out.println("Goodbye!");
                    System.exit(1);
                }
                else{
                    System.out.println("Unsupported option. Please try again!");
                }
            }   
        }catch(DataAccessException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }catch(DataFormatException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        

    }
    public ArrayList<Competition> getCompetitionList(){
        return competitionList;
    }
    public void setCompetitionList(ArrayList<Competition> list){
        competitionList=list;
    }
    /**
    *@param str the string we need to decide 
    *@return true if it is numeric
    */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){   
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    /**
    *@return true if a competition is active
    */
    public boolean existActiveCompetition(){
        boolean active=false;
        for(int i=0;i<this.getCompetitionList().size();i++){
            if(this.getCompetitionList().get(i).getState().equals("active")){
                active=true;
                mode=this.getCompetitionList().get(i).getMode();
            }
        }
        return active;

    }
    /**
    *update bills.csv
    *@param dp record the changes of bills and members  
    *@throws DataAccessException
    */
    public void updatBill(DataProvider dp) throws DataAccessException{
        BufferedWriter bw=null;
        try{   
            bw=new BufferedWriter(new FileWriter("bills.csv"));
            for(Bill bill:dp.getBillList()){
                if(bill.getMemberId()!=null){
                    String line=bill.getBillId()+","+bill.getMemberId()+","+bill.getAmount()+","+bill.getUsed();
                    bw.write(line);
                    bw.newLine();
                }
                else{
                    String line=bill.getBillId()+","+bill.getAmount()+","+bill.getUsed();
                    bw.write(line);
                    bw.newLine();
                }
            }                          
        }catch(Exception e){
            throw new DataAccessException("Error when writing the file.");
        }
        finally{
                try{
                    bw.flush();
                    bw.close();
                    System.out.println("The bill file has also been automatically updated.");
                    }catch(IOException e){
                        throw new DataAccessException("Error when closing file. ");
                    }
        }
    }
}
