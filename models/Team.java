package models;

import java.util.ArrayList;

public class Team {
    private ArrayList<Employee> employees;  // list of all the employees contained in the team
    private String name;    // Name of the team
    private String description; // A description of the


    /**
     * Constructs a Department object with the specified name and description.
     * @param name Name to be given to the department.
     * @param description Description to be given to the department.
     */
    public Team(String name, String description) {
        this.name = name;
        this.description = description;
        this.employees = new ArrayList<Employee>();

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


    @Override
    public String toString() {
        return this.getName();
    }

}
