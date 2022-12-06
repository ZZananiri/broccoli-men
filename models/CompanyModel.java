package models;

import java.util.*;

/**
 * A complete class representation of the state of a company.
 */
public class CompanyModel implements IExpensable{
    private String name;    // Name of the company
    private String description; // A description of the company
    private final ArrayList<Department> departments;  // All the departments in the company
    private int teamCount;  // Count of the number of teams in the company
    private int employeeCount;  // Count of the number of employees in the company
    private double teamSalaryBudgets;  // The allocated budget for each team of the company
    // List of subscribers to the changes in team salary budgets
    private final ArrayList<ISalaryBudgetSubscriber> salaryBudgetSubscribers;

    /**
     * Constructs a CompanyModel object.
     */
    public CompanyModel() {
        this.name = "Default Company";
        this.description = "Click the \"Edit Company Details\" button to edit the details of your company!";
        this.departments = new ArrayList<Department>();
        this.teamCount = 0;
        this.employeeCount = 0;
        this.teamSalaryBudgets = 0;
        this.salaryBudgetSubscribers = new ArrayList<ISalaryBudgetSubscriber>();
    }

    /**
     * Appends the specified department object to the end of the company's list of departments.
     * @param department element to be added to the company's list of departments.
     */
    public void addDepartment(Department department){this.departments.add(department);}

    /**
     * Removes the first occurrence of the specified department object in the company's list of departments.
     * The company's list of departments is unchanged if it doesn't contain the department object argument.
     * @param department element to be removed from company's list of departments, if contained.
     */
    public void removeDepartment(Department department) {this.departments.remove(department);}

    /**
     * Returns a list of the company's departments.
     * @return an ArrayList containing the company's departments.
     */
    public ArrayList<Department> getDepartments(){return this.departments;}

    /**
     * Returns the name of the company.
     * @return the name of the company.
     */
    public String getName() {return this.name;}

    /**
     * Sets the company's name to the specified string.
     * @param name the new name of the company.
     */
    public void setName(String name) {this.name = name; }

    /**
     * Returns the company's description.
     * @return the company's description string.
     */
    public String getDescription() {return this.description;}

    /**
     * Sets the company's description to the specified string.
     * @param description the new description of the company.
     */
    public void setDescription(String description) {this.description = description; }

    /**
     * Returns the count of teams in the company.
     * @return the count of teams in the company.
     */
    public int getTeamCount() {return this.teamCount;}
    /**
     * Increments the team count
     *
     */
    public void incrementTeamCount(int inc){this.teamCount += inc;}

    /**
     * Returns the count of employees in the company.
     * @return the count of employees in the company.
     */
    public int getEmployeeCount() {return this.employeeCount;}

    /**
     * Increments the employee count
     *
     */
    public void incrementEmployeeCount(int inc){this.employeeCount += inc;}

    /**
     * Returns the company's salary budget.
     * @return the company's salary budget.
     */
    public double getTeamSalaryBudgets() {
        return this.teamSalaryBudgets;
    }

    /**
     * Sets the salary budget of each of the company's teams to the specified budget amount.
     * @param teamSalaryBudgets the new salary budget of each of the company's teams.
     */
    public void setTeamSalaryBudget(double teamSalaryBudgets) {
        this.teamSalaryBudgets = teamSalaryBudgets;
        notifySalaryBudgetSubscribers();
    }

    /**
     * Adds the specified ISalaryBudgetSubscriber object to the company's list of salary budget subscribers.
     * @param subscriber the ISalaryBudgetSubscriber object to add to the company's list of salary budget subscribers.
     */
    public void addSalaryBudgetSubscriber(ISalaryBudgetSubscriber subscriber) {
        if (!this.salaryBudgetSubscribers.contains(subscriber)) {
            this.salaryBudgetSubscribers.add(subscriber);
        }
    }

    /**
     * Removes the specified ISalaryBudgetSubscriber object from the company's list of salary budget subscribers.
     * @param subscriber the ISalaryBudgetSubscriber object to remove from the company's list of salary budget subscribers.
     */
    public void removeSalaryBudgetSubscriber(ISalaryBudgetSubscriber subscriber) {
        this.salaryBudgetSubscribers.remove(subscriber);
    }

    /**
     * Returns the company's salary expense.
     * @return the company's salary expense.
     */
    @Override
    public double getSalaryExpense() {
        double salaryExpense = 0.0;
        for (Department department : getDepartments()){
            salaryExpense+= department.getSalaryExpense();
        }
        return salaryExpense;
    }

    /**
     * Notify the salary budget subscribers to update their salary budgets.
     */
    private void notifySalaryBudgetSubscribers() {
        for (ISalaryBudgetSubscriber salaryBudgetSubscriber : salaryBudgetSubscribers) {
            salaryBudgetSubscriber.updateSalaryBudget(teamSalaryBudgets);
        }
    }
}
