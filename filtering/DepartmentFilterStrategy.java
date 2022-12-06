package filtering;

import models.Employee;

/**
 * Filter strategy class for filtering employee data by department name
 */
public class DepartmentFilterStrategy implements FilterStrategy{
    /**
     * Get the current filtering strategy.
     *
     * @param employee the employee to test the filter on
     * @param lowerCaseFilter the current filter keyword put to lowercase
     * @return the current filtering strategy
     */
    public boolean filterEmployees(Employee employee, String lowerCaseFilter) {
        return employee.getDepartment().toString().toLowerCase().startsWith(lowerCaseFilter);
    }

    /**
     * Creates a string representation of the name of the filter.
     *
     * @return a string representation of the name of the filter.
     */
    @Override
    public String toString() {
        return "Filter Department";
    }
}
