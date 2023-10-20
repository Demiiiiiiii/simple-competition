/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.io.Serializable;

public class Bill implements Serializable{
    private String billId;
    private String memberId;
    private String totalAmount;
    private String used;
    
    //setter
    public void setBillId(String id){
         billId=id;
     }
    public void setMemberId(String id){
         memberId=id;
     }
     public void setAmount(String amount){
         totalAmount=amount;
     }
     public void setUsed(String u){
         used=u;
     }
    //getter
     public String getBillId(){
         return billId;
     }
     public String getMemberId(){
         return memberId;
     }
     public String getAmount(){
         return totalAmount;
     }
     public String getUsed(){
         return used;
     }

}
