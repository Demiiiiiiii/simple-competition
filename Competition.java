/*
 * Student name: Jiayun Huang
 * Student ID: 1249398
 * LMS username: Jiayunh3
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

public abstract class Competition implements Serializable{
    private String name; //competition name
    private int id; //competition identifier
    private String mode; // test or normal
    private String state; // active or completed
    private String type; // type of competition
    

    public abstract void addEntries();

    public abstract void drawWinners();
    
    public void report() {
    }
    //setter
    public void setId(int i){
        id=i;
    }
    public void setName(String n){
        name=n;
    }
    public void setMode(String n){
        mode=n;
    }
    public void setState(String n){
        state=n;
    }
    public void setType(String n){
        type=n;
    }
    // getter
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getMode(){
        return mode;
    }
    public String getState(){
        return state;
    }
    public String getType(){
        return type;
    }
} 
