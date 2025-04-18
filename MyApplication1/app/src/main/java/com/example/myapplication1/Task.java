package com.example.myapplication1;

public class Task {
    private int id;
    private String description;
    private boolean isCompleted;
    private boolean isHolded;
    private String type; // "work" or "personal"

    public Task(int id, String description, boolean isCompleted, boolean isHolded, String type) {
        this.id = id;
        this.description = description;
        this.isCompleted = isCompleted;
        this.isHolded = isHolded;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isHolded() {
        return isHolded;
    }

    public String getType() {
        return type;
    }

    // Add setter methods for isCompleted and isHolded to allow updating the task state
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setHolded(boolean holded) {
        isHolded = holded;
    }

    // Optional: Add a setter for description if you want to modify it later
    public void setDescription(String description) {
        this.description = description;
    }

    // Optional: Add a setter for type if you want to modify the task's type later
    public void setType(String type) {
        this.type = type;
    }
}
