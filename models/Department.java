package models;

import java.util.ArrayList;

/**
 * A complete class representation of the state of a company department.
 */
public class Department implements IExpensable{
    private ArrayList<Team> teams;  // All the teams contained in the department
    private String name;    // Name of the department
    private String description; // A description of the department
    private int employeeCount;  // Count of the number of employees in the department
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
        this.employeeCount = 0;
        this.salaryBudget = 0.0;
        this.salaryExpense = 0.0;
    }
    /**
     * Appends the specified teams object to the end of the department's list of teams.
     * @param team element to be added to the department's list of teams.
     */
    public void addTeam(Team team){this.teams.add(team);}

    /**
     * Removes the first occurrence of the specified team object in the department's list of teams.
     * The department's list of teams is unchanged if it doesn't contain the team object argument.
     * @param team element to be removed from department's list of teams, if contained.
     */
    public void removeTeam(Team team) {this.teams.remove(team);}

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
     * Returns the count of employees in the department.
     * @return the count of employees in the department.
     */
    public int getEmployeeCount() {return this.employeeCount;}

    /**
     * Increments the employee count
     *
     */
    public void incrementEmployeeCount(int inc){this.employeeCount += inc;}

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
    @Override
    public double getSalaryExpense() {
        double salaryExpense = 0.0;
        for (Team team : getTeams()){
            salaryExpense += team.getSalaryExpense();
        }
        return salaryExpense;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
