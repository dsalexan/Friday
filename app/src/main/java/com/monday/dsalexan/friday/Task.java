package com.monday.dsalexan.friday;

import java.util.ArrayList;

/**
 * Created by Danilo on 09/07/2017.
 */

public class Task {
    private Integer id;
    private String title;
    private Status status;

    private ArrayList<Reminder> reminders;


    // CONSTRUCTORS
    public enum Status{
        ACTIVE(1), EXPIRED(2), DELETED(3), FOCUSED(4);

        private final int value;
        Status(int v){
            this.value = v;
        }

        public int getValue(){
            return this.value;
        }

        public static Status fromInteger(int i){
            Status[] Ss = Status.values();
            for (Status s : Ss) {
                if(s.getValue() == i) return s;
            }

            return null;
        }
    }

    public Task(Integer id){
        this.id = id;
        this.setTitle(null);

        this.reminders = new ArrayList<>();
    }

    public Task(Integer id, String title) {
        this(id);
        this.setTitle(title);
    }

    public Task(Integer id, String title, Status status) {
        this(id, title);
        this.status = status;
    }

    public Task(Integer id, String title, Status status, ArrayList<Reminder> reminders) {
        this(id, title, status);
        setReminders(reminders);
    }


    // OVERRIDES
    @Override
    public boolean equals(Object o) {
        return this.getId().equals(((Task) o).getId());
    }

    @Override
    public String toString() {
        return "(" + this.getId().toString() + ") " + this.getTitle();
    }


    // CAPSULES
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title == null ? "Empty title :(" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public ArrayList<String> getFlatReminders(){
        ArrayList<String> str = new ArrayList<>();
        for (Reminder r : reminders) {
            str.add(r.toString());
        }

        return str;
    }

    public void setReminders(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }

    public void addReminder(Reminder reminder){
        this.reminders.add(reminder);
    }

    public void removeReminder(Reminder reminder){
        this.reminders.remove(reminder);
    }
}
