package models;

import java.util.Objects;
public class Employee{
    private String firstName;    // Firstname of the employee
    private String lastName;     // Lastname of the employee
    private float salary;         // Salary of the employee
    private String gender;        // Gender of the employee
    private int age;              // Age of the employee
    private int employeeID;       // employee ID of the employee

    /**
     * Constructs an Employee object with the specified name and salary.
     * @param firstName Firstname to be given to the employee.
     * @param lastName Lastname to be given to the employee.
     * @param salary Salary to be given to the employee.
     * @param gender Gender to be given to the employee.
     * @param age Age to be given to the employee.
     */
    public Employee(String firstName, String lastName, float salary, String gender, int age, int employeeID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.gender = gender;
        this.age = age;
        this.employeeID = employeeID;
    }

    /**
     * Returns the employee's full name.
     * @return the employee's full name.
     */
    public String getFullName() {
        return this.firstName + this.lastName;
    }

    /**
     * Returns the employee's first name.
     * @return the employee's first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the employee's last name.
     * @return the employee's last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Returns the employee's salary.
     * @return the employee's salary.
     */
    public float getSalary() {
        return this.salary;
    }

    /**
     * Returns the employee's ID.
     * @return the employee's ID.
     */
    public int getEmployeeID() {
        return this.employeeID;
    }

    /**
     * Returns the employee's gender.
     * @return the employee's gender.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Sets the employee's salary to the specified float.
     * @param salary the new salary of the employee.
     */
    public void setSalary(float salary) {this.salary = salary; }
}