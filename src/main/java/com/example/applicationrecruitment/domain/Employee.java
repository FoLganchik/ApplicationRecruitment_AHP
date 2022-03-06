package com.example.applicationrecruitment.domain;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String requestSalary;
    private String description;

    public Employee(String firstName, String lastName, int age, String requestSalary, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.requestSalary = requestSalary;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRequestSalary() {
        return requestSalary;
    }

    public void setRequestSalary(String requestSalary) {
        this.requestSalary = requestSalary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
