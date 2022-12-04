package models;

public class Employee implements IExpensable{
    private String firstName;    // Firstname of the employee
    private String lastName;     // Lastname of the employee
    private double salary;         // Salary of the employee
    private String gender;        // Gender of the employee
    private int age;              // Age of the employee
    private int employeeID;       // employee ID of the employee
    private Team team;
    private Department department;

    /**
     * Constructs an Employee object with the specified name and salary.
     * @param firstName Firstname to be given to the employee.
     * @param lastName Lastname to be given to the employee.
     * @param salary Salary to be given to the employee.
     * @param gender Gender to be given to the employee.
     * @param age Age to be given to the employee.
     */
    public Employee(String firstName, String lastName, double salary, String gender, int age, int employeeID, Team team, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.gender = gender;
        this.age = age;
        this.employeeID = employeeID;
        this.team = team;
        this.department = department;
    }

    /**
     * Returns the employee's full name.
     * @return the employee's full name.
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Returns the employee's first name.
     * @return the employee's first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the employee's first name to the specified string.
     * @param firstName the new first name of the employee
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the employee's last name.
     * @return the employee's last name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the employee's last name to the specified string.
     * @param lastName the new last name of the employee
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the employee's salary.
     * @return the employee's salary.
     */
    public double getSalary() {
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
     * Sets the employee's gender to the specified string.
     * @param gender the new gender of the employee
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the employee's salary to the specified float.
     * @param salary the new salary of the employee.
     */
    public void setSalary(float salary) {this.salary = salary; }

    /**
     * Returns the employee's team.
     * @return the employee's team.
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Sets the employee's team to the specified team
     * @param team the new team of the employee
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Returns the employee's department.
     * @return the employee's department.
     */
    public Department getDepartment() {
        return this.department;
    }

    /**
     * Sets the employee's department to the specified department
     * @param department the new department of the employee
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Returns the employee's age.
     * @return the employee's age.
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Sets the employee's age to the specified number.
     * @param age the new age of the employee
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the employee's salary.
     * @return the employee's salary.
     */
    @Override
    public double getSalaryExpense() {
        return this.salary;
    }
}