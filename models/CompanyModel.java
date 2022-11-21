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
        this.departments = new ArrayList<Department>();
        this.teams = new ArrayList<Team>();
        this.employees = new ArrayList<Employee>();
    }

    public void addDepartment(Department department){
        this.departments.add(department);
    }
    public void removeDepartment(Department department) {
        this.departments.remove(department);
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
