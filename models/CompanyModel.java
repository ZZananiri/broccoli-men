package models;

import java.util.*;

/**
 * A complete class representation of the state of a company.
 */
public class CompanyModel {
    private final ArrayList<Department> departments;  // All the departments in the company
    private final ArrayList<Team> teams;  // All the teams in the company
    private final ArrayList<Employee> employees;  // All the employees in the company
    private String name;    // Name of the company
    private String description; // A description of the company

    /**
     * Constructs a CompanyModel object.
     */
    public CompanyModel() {
        this.name = "Default Company";
        this.description = "This is a default company; modify me to your liking!";
        this.departments = new ArrayList<Department>();
        this.teams = new ArrayList<Team>();
        this.employees = new ArrayList<Employee>();
    }

    /**
     * Appends the specified department object to the end of the company's list of departments.
     * @param department element to be added to the company's list of departments.
     */
    public void addDepartment(Department department){
        this.departments.add(department);
    }

    /**
     * Removes the first occurrence of the specified department object in the company's list of departments.
     * The company's list of departments is unchanged if it doesn't contain the department object argument.
     * @param department element to be removed from company's list of departments, if contained.
     */
    public void removeDepartment(Department department) {
        this.departments.remove(department);
    }

    /**
     * Returns a list of the company's departments.
     * @return an ArrayList containing the company's departments.
     */
    public ArrayList<Department> getDepartments(){
        return this.departments;
    }

    /**
     * Returns a list of the company's teams.
     * @return an ArrayList containing the company's teams.
     */
    public ArrayList<Team> getTeams(){
        return this.teams;
    }

    /**
     * Returns a list of the company's employees.
     * @return an ArrayList containing the company's employees.
     */
    public ArrayList<Employee> getEmployees(){
        return this.employees;
    }

    /**
     * Returns the name of the company.
     * @return the name of the company.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the company's name to the specified string.
     * @param name the new name of the company.
     */
    public void setName(String name) {this.name = name; }

    /**
     * Returns the company's description.
     * @return the company's description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the company's description to the specified string.
     * @param description the new description of the company.
     */
    public void setDescription(String description) {this.description = description; }
}
