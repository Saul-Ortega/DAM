package org.example;

import java.util.List;

public class Department {
    //MEMBERS
    private String name;
    private String localization;
    private List<Employee> employees;

    //EMPTY CONSTRUCTOR
    public Department() {}

    //FULL CONSTRUCTOR
    public Department(String name, String localization, List<Employee> employees) {
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

    public List<Employee> getEmployees() {
        return this.employees;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
