package org.example;

public class Department {
    //MEMBERS
    private String name;
    private String localization;
    private Employee[] employees;

    //EMPTY CONSTRUCTOR
    public Department() {}

    //FULL CONSTRUCTOR
    public Department(String name, String localization, Employee[] employees) {
        this.name = name;
        this.localization = localization;
        this.employees = employees;
    }

    //GETTERS
    public String getName() {
        return this.name;
    }

    public String getLocalization() {
        return this.localization;
    }

    public Employee[] getEmployees() {
        return this.employees;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public void setEmployees(Employee[] employees) {
        this.employees = employees;
    }
}
