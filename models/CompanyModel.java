package models;

import java.util.*;

public class CompanyModel {
    private ArrayList<Department> departments;
    private ArrayList<Team> teams;
    private ArrayList<Employee> employees;
    private String name;
    private String description;

    public CompanyModel() {
        this.name = "Default Company";
        this.description = "This is a default company; modify me to your liking!";
    }

    public ArrayList<Department> getDepartments(){
        return this.departments;
    }
    public ArrayList<Team> getTeams(){
        return this.teams;
    }
    public ArrayList<Employee> getEmployees(){
        return this.employees;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {this.name = name; }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {this.description = description; }
}
