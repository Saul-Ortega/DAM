package org.example;

public class Employee {
    //MEMBERS
    private String id;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private Department department;

    //EMPTY CONSTRUCTOR
    public Employee() {}

    //FULL CONSTRUCTOR
    public Employee(String id, String name, String surname, String address, String phone, Department department) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.department = department;
    }

    //GETTERS
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }

    public Department getDepartment() {
        return this.department;
    }

    //SETTERS
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
