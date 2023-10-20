/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */
import java.io.Serializable;

public class Member implements Serializable{
     private String memberId;
     private String memberName;
     private String email;
     
     //setter
     public void setId(String id){
         memberId=id;
     }
     public void setName(String name){
         memberName=name;
     }
     public void setEmail(String e){
         email=e;
     }
     //getter
     public String getId(){
         return memberId;
     }
     public String getName(){
         return memberName;
     }
     public String getEmail(){
         return email;
     }
}
