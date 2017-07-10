package com.monday.dsalexan.friday;

/**
 * Created by Danilo on 09/07/2017.
 */

public class Reminder {
    private String date;

    public Reminder(String date){
        this.setDate(date);
    }

    @Override
    public String toString(){
        return this.getDate();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
