package com.monday.dsalexan.friday;

/**
 * Created by Danilo on 09/07/2017.
 */

public class Task {
    private Integer id;
    private String title;

    public enum Status{ACTIVE, EXPIRED, DELETED, FOCUSED}

    public Task(Integer id){
        this.id = id;
        this.setTitle(null);
    }

    public Task(Integer id, String title) {
        this.id = id;
        this.setTitle(title);
    }

    @Override
    public boolean equals(Object o) {
        return this.getId().equals(((Task) o).getId());
    }

    @Override
    public String toString() {
        return "(" + this.getId().toString() + ") " + this.getTitle();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title == null ? "Empty title :(" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
