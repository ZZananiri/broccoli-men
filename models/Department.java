package models;

import java.util.ArrayList;

public class Department {
    private ArrayList<Team> teams;
    private ArrayList<Employee> employees;
    private String name;
    private String description;

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
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
