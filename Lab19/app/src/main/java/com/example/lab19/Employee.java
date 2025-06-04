package com.example.lab19;

import androidx.annotation.NonNull;

public class Employee {
    private String id;
    private String title;
    private String name;
    private String phone;

    public Employee() {
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "-" + title + "-" + name + "-" + phone;
    }
}