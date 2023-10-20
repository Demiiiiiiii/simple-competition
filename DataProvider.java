/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

public class DataProvider implements Serializable{
    /**
     * 
     * @param memberFile A path to the member file (e.g., members.csv)
     * @param billFile A path to the bill file (e.g., bills.csv)
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the the content is incorrect
     */
     private ArrayList<Member> memberList=new ArrayList<Member>();
     private ArrayList<Bill> billList=new ArrayList<Bill>();


    //constructor
     public DataProvider(String memberFile, String billFile) 
                        throws DataAccessException, DataFormatException {
        
        String len;
        String[] chunks;
        String currentLine;
        try {
				// Load the member file.
                BufferedReader memberReader = new BufferedReader(new FileReader(memberFile));
                currentLine = memberReader.readLine();
                while(currentLine!=null) 
                {   
                    chunks = currentLine.split(",");
                    try
                    {
                        if(chunks.length!=3)
                        {
                            memberReader.close();
                            throw new DataFormatException();
                        }   
                        else
                        {   Member member=new Member();
                            member.setId(chunks[0]);
                            member.setName(chunks[1]);
                            member.setEmail(chunks[2]);                         
                            memberList.add(member);
                            currentLine = memberReader.readLine();  
                        }
                    } catch(Exception e){
                        memberReader.close();
                        throw new DataFormatException();
                    }
                 }  
                // Reached the end of the file, so stop reading.
                memberReader.close();
                // Load the bill file.
                BufferedReader billReader = new BufferedReader(new FileReader(billFile));
                currentLine = billReader.readLine();
                while(currentLine!=null)
                {                   
                    chunks = currentLine.split(",");
                        try
                        {
                            if(chunks.length==4)
                            {
                                if(chunks[0].length()!=6)
                                {
                                    billReader.close();
                                    throw new DataFormatException("File format is invalid.");
                                }
                                else
                                {   Bill bill = new Bill();
                                    bill.setBillId(chunks[0]);
                                    bill.setMemberId(chunks[1]);
                                    bill.setAmount(chunks[2]);
                                    bill.setUsed(chunks[3]);
                                    billList.add(bill);
                                    currentLine = billReader.readLine();   
                                }
                            }
                           
                        } catch(Exception e)
                        {
                            billReader.close();
                            throw new DataFormatException("File format is invalid.");
                        }
                          
                }                     
                // Reached the end of the file, so stop reading.
                billReader.close();
                this.setBillList(billList);
			} catch (FileNotFoundException e) {
				throw new DataAccessException("File cannot find.");
			} catch (IOException e) {
			    throw new DataAccessException("File cannot read.");
            }
	}
    
   //getter
    public Member getMember(String id){
        Member member=new Member();
        Member m=new Member();
        for(int i=0;i<memberList.size();i++){
            member=memberList.get(i);
            if (id.equals(member.getId())){
                m=member;
            }
        }
        return m;
    }
    public Bill getBill(String id){
        Bill bill=new Bill();
        Bill b=new Bill();
        for(int i=0;i<billList.size();i++){
            bill=billList.get(i);
            if (id.equals(bill.getBillId())){
                b=bill;
            }
        }
        return b;
    }
    public ArrayList<Bill> getBillList(){
        return billList;
    }
    //setter
    public void setBillList(ArrayList<Bill> list){
         billList=list;
     }
}
              

         

