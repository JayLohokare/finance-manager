package com.codeitsuisse.team92.expensetracker;

/**
 * Created by Jay Lohokare on 12-Sep-15.
 */

public class ExpenseDetails {
    private String from;
    private String to;
    private int date,month, year;
    private int amount;
    private String description;
    private String category;
    private int sr;
    private int liability;

    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
    public int getDate(){
        return date;
    }
    public int getMonth(){
        return month;
    }
    public int getSr(){
        return sr;
    }
    public int getLiability(){
        return liability;
    }

    public int getYear(){
        return year;
    }

    public int getAmount(){
        return amount;
    }
    public String getDescription(){
        return description;
    }
    public String getCategory(){
        return category;
    }

    void setFrom(String from){
        this.from = from;
    }
    void setTo(String to){
        this.to = to;
    }
    void setDate(int date){
        this.date = date;
    }
    void setMonth(int month){
        this.month = month;
    }
    void setSr(int sr){
        this.sr = sr;
    }
    void setLiability(int liability){ this.liability = liability;}

    void setYear(int year){
        this.year = year;
    }


    void setAmount(int amount){
        this.amount = amount;
    }
    void setDescription(String description){
        this.description = description;
    }
    void setCategory(String category){
        this.category = category;
    }

}