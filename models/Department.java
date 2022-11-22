package models;

import java.util.ArrayList;

/**
 * A complete class representation of the state of a company department.
 */
public class Department {
    private ArrayList<Team> teams;  // All the teams contained in the department
    private String name;    // Name of the department
    private String description; // A description of the department
    private double salaryBudget;  // The salary budget of the department
    private double salaryExpense;   // The salary expense of the department

    /**
     * Constructs a Department object with the specified name and description.
     * @param name Name to be given to the department.
     * @param description Description to be given to the department.
     */
    public Department(String name, String description) {
        this.name = name;
        this.description = description;
        this.teams = new ArrayList<Team>();
        this.salaryBudget = 0.0;
        this.salaryExpense = 0.0;
    }

    /**
     * Returns a list of the department's teams.
     * @return an ArrayList containing the department's teams.
     */
    public ArrayList<Team> getTeams(){
        return this.teams;
    }

    /**
     * Returns the department's name.
     * @return the department's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the department's name to the specified string.
     * @param name the new name of the department.
     */
    public void setName(String name) {this.name = name; }

    /**
     * Returns the department's description.
     * @return the department's description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the department's description to the specified string.
     * @param description the new description of the department.
     */
    public void setDescription(String description) {this.description = description; }

    /**
     * Returns the department's salary budget.
     * @return the department's salary budget.
     */
    public double getSalaryBudget() {
        return this.salaryBudget;
    }

    /**
     * Returns the department's salary expense.
     * @return the department's salary expense.
     */
    public double getSalaryExpense() {
        return this.salaryExpense;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
