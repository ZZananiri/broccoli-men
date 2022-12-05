package models;

/**
 * Interface for objects that need to update their salary budget.
 */
public interface ISalaryBudgetSubscriber {
    /**
     * Update the salary budget of the object to the specified amount.
     *
     * @param new_budget the new salary budget amount.
     */
    void updateSalaryBudget(double new_budget);
}
