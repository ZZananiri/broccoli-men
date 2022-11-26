package models;

import java.util.ArrayList;

public class Team {
    private ArrayList<Employee> employees;  // list of all the employees contained in the team
    private String name;    // Name of the team
    private String description; // A description of the team
    private double salaryBudget; // Budget of salary of the team
    private double salaryExpense; // The salary expense of the


    /**
     * Constructs a Team object with the specified name and description.
     * @param name Name to be given to the team.
     * @param description Description to be given to the team.
     */
    public Team(String name, String description) {
        this.name = name;
        this.description = description;
        this.employees = new ArrayList<Employee>();
        this.salaryBudget = 0.0;
        this.salaryExpense = 0.0;
    }

    /**
     * Returns the team's name.
     * @return the team's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the team's name to the specified string.
     * @param name the new name of the team.
     */
    public void setName(String name) {this.name = name; }

    /**
     * Returns the team's description.
     * @return the team's description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the team's description to the specified string.
     * @param description the new description of the team.
     */
    public void setDescription(String description) {this.description = description; }

    /**
     * Returns the list of employees in the team.
     * @return arraylist of employees in the team
     */
    public ArrayList<Employee> getEmployees() {return this.employees;}

    /**
     * Returns the team's salary budget.
     * @return the team's salary budget.
     */
    public double getSalaryBudget() {
        return this.salaryBudget;
    }

    /**
     * Returns the team's salary expense.
     * @return the team's salary expense.
     */
    public double getSalaryExpense() {
        return this.salaryExpense;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
