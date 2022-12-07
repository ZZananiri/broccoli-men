package filtering;

import models.Employee;

/**
 * Interface for objects that determine the attribute for filtering employee data.
 */
public interface FilterStrategy {

    /**
     * Get the current filtering strategy.
     *
     * @param employee the employee to test the filter on
     * @param lowerCaseFilter the current filter keyword put to lowercase
     * @return the current filtering strategy
     */
    boolean filterEmployees(Employee employee, String lowerCaseFilter);
}
