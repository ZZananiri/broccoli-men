package models;

import java.util.ArrayList;

public class Team implements IExpensable, ISalaryBudgetSubscriber{
    private ArrayList<Employee> employees;  // list of all the employees contained in the team
    private String name;    // Name of the team
    private String description; // A description of the team
    private double salaryBudget; // Budget of salary of the team


    /**
     * Constructs a Team object with the specified name and description.
     * @param name Name to be given to the team.
     * @param description Description to be given to the team.
     */
    public Team(String name, String description, double salaryBudget) {
        this.name = name;
        this.description = description;
        this.employees = new ArrayList<Employee>();
        this.salaryBudget = salaryBudget;
    }

    @Override
    public void updateSalaryBudget(double available_budget) {
        this.salaryBudget = available_budget;
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
    @Override
    public double getSalaryExpense() {
        double salaryExpense = 0.0;
        for (Employee employee : getEmployees())
        {
            salaryExpense+= employee.getSalaryExpense();
        }
        return salaryExpense;
    }

    /**
     * Appends the specified employee object to the end of the teams list of employees.
     * @param employee element to be added to the teams list of employees.
     */
    public void addEmployee(Employee employee){this.employees.add(employee);}

    /**
     * Removes the specified employee object from the teams list of employees.
     * @param employee element to be removed from the teams list of employees.
     */
    public void removeEmployee(Employee employee){this.employees.remove(employee);}

    @Override
    public String toString() {
        return this.getName();
    }

}
