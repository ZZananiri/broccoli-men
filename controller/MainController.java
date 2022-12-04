package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CompanyModel;
import models.Department;
import models.Team;
import models.Employee;
import views.MainView;
import views.WarningPopup;

import javax.swing.*;
import java.util.EventListener;

/**
 * Controller class responsible for handling events invoked by view elements, and updating the model and the view
 * accordingly.
 */
public class MainController {
    // Company names and descriptions are not to exceed these lengths
    private final int MAX_COMPANY_NAME_LENGTH = 16;
    private final int MAX_COMPANY_DESCRIPTION_LENGTH = 270;

    // Department names and descriptions are not to exceed these lengths
    private final int MAX_DEPARTMENT_NAME_LENGTH = 20;
    private final int MAX_DEPARTMENT_DESCRIPTION_LENGTH = 95;

    // Team names and descriptions are not to exceed these lengths
    private final int MAX_TEAM_NAME_LENGTH = 20;
    private final int MAX_TEAM_DESCRIPTION_LENGTH = 95;

    // Employee names and salaries are not to exceed these lengths
    private final int MAX_EMPLOYEE_FIRST_NAME_LENGTH = 10;
    private final int MAX_EMPLOYEE_LAST_NAME_LENGTH = 10;
    private final int MAX_EMPLOYEE_SALARY_LENGTH = 10;
    private final int MAX_EMPLOYEE_AGE_LENGTH = 2;
    private int EMPLOYEE_ID = 1000;

    private CompanyModel model; // The model of the company
    private MainView view;  // The view in which this controller is responsible for

    /**
     * Constructs a MainController object with the specified view and company model.
     * @param model The model of the company.
     * @param view The view in which this controller is responsible for.
     */
    public MainController(CompanyModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Updates all the UI elements displaying the details of the company.
     */
    private void updateCompanyDetailsUI() {
        companyNameTxt.setText(model.getName());
        companyDescriptionTxt.setText(model.getDescription());
        departmentCountTxt.setText("Number of Departments: " + model.getDepartments().size());
        teamCountTxt.setText("Number of Teams: " + model.getTeamCount());
        employeeCountTxt.setText("Number of Employees: " + model.getEmployeeCount());
        companySalaryBudgetTxt.setText("Salary Budget: " + model.getSalaryBudget());
        companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());
    }

    // === COMPANY SECTION UI ELEMENTS =======================================================
    @FXML
    private Text companyNameTxt;
    @FXML
    private Text companyDescriptionTxt;
    @FXML
    private Text departmentCountTxt;
    @FXML
    private Text teamCountTxt;
    @FXML
    private Text employeeCountTxt;
    @FXML
    private Text companySalaryBudgetTxt;
    @FXML
    private Text companySalaryExpenseTxt;
    @FXML
    private TextField salaryBudgetField;

    // === DEPARTMENT SECTION UI ELEMENTS ============================================================
    @FXML
    private ComboBox<Department> departmentsComboBox;
    @FXML
    private Text selectedDepartmentName;
    @FXML
    private Text departmentDescriptionTxt;
    @FXML
    private Text selectedDepartmentBudget;
    @FXML
    private Text selectedDepartmentExpenses;
    @FXML
    private Text selectedDepartmentTeamCount;
    @FXML
    private Text selectedDepartmentEmployeeCount;

    // === TEAM SECTION UI ELEMENTS ======================================================================
    @FXML
    private ComboBox<Team> teamsComboBox;
    @FXML
    private Text selectedTeamName;
    @FXML
    private Text teamDescriptionTxt;
    @FXML
    private Text selectedTeamBudget;
    @FXML
    private Text selectedTeamExpenses;
    @FXML
    private Text selectedTeamEmployeeCount;

    // === EMPLOYEE RECORDS TABLE UI ELEMENTS ================================================================
    @FXML
    private TableView<Employee> employeeRecordsTable;
    @FXML
    private TableColumn<Employee, Integer> employeeIdCol;
    @FXML
    private TableColumn<Employee, String> firstNameCol;
    @FXML
    private TableColumn<Employee, String> lastNameCol;
    @FXML
    private TableColumn<Employee, Team> teamCol;
    @FXML
    private TableColumn<Employee, Department> departmentCol;
    @FXML
    private TableColumn<Employee, Integer> ageCol;
    @FXML
    private TableColumn<Employee, Float> salaryCol;
    @FXML
    private TableColumn<Employee, String> genderCol;

    // === COMPANY SECTION EVENT HANDLERS ================================================================
    @FXML
    private void editCompanyDetails(ActionEvent event) {
        // Creating a stage on which the user will be able to edit company details
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(view.getStage());
        dialog.setTitle("Edit Company Details");
        dialog.setResizable(false);

        // Defining the elements on the stage
        TextField companyNameField = new TextField();
        TextArea companyDescriptionField = new TextArea();
        Button setCompanyNameBtn = new Button();
        Button setCompanyDescriptionBtn = new Button();
        Text topNote = new Text(MAX_COMPANY_NAME_LENGTH + " characters remaining.");
        Text bottomNote = new Text(MAX_COMPANY_DESCRIPTION_LENGTH + " characters remaining.");

        // Configuring elements
        companyNameField.setPromptText("New company name");
        companyDescriptionField.setPromptText("New company description");
        companyDescriptionField.setWrapText(true);
        setCompanyNameBtn.textProperty().set("Set new name");
        setCompanyDescriptionBtn.textProperty().set("Set new description");

        // Defining event handlers for interactions with elements

        // Updating the name character limit note as the user types
        companyNameField.setOnKeyTyped(e -> {
            int length = companyNameField.getLength();
            topNote.setText(length > MAX_COMPANY_NAME_LENGTH ?
                    "Your input will be truncated" :
                    MAX_COMPANY_NAME_LENGTH - length + " characters remaining.");
        });

        // Updating the description character limit note as the user types
        companyDescriptionField.setOnKeyTyped(e -> {
            int length = companyDescriptionField.getLength();
            bottomNote.setText(length > MAX_COMPANY_DESCRIPTION_LENGTH ?
                    "Your input will be truncated" :
                    MAX_COMPANY_DESCRIPTION_LENGTH - length + " characters remaining.");
        });

        // Updating the company's name on set name button click
        setCompanyNameBtn.setOnAction(e -> {
            String name = companyNameField.getText().substring(0, Math.min(MAX_COMPANY_NAME_LENGTH, companyNameField.getLength()));
            model.setName(name);
            companyNameTxt.setText(name);
        });

        // Updating the company's description on set description button click
        setCompanyDescriptionBtn.setOnAction(e -> {
            String description = companyDescriptionField.getText().substring(0, Math.min(MAX_COMPANY_DESCRIPTION_LENGTH, companyDescriptionField.getLength()));
            model.setDescription(description);
            companyDescriptionTxt.setText(description);
        });

        // Creating layout for the scene
        HBox topHbox = new HBox(companyNameField, topNote, setCompanyNameBtn);
        topHbox.setSpacing(10);
        HBox bottomHbox = new HBox(setCompanyDescriptionBtn, bottomNote);
        bottomHbox.setSpacing(10);
        VBox dialogVbox = new VBox(topHbox, companyDescriptionField, bottomHbox);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);

        // Setting a scene on the stage and showing the stage
        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    @FXML
    private void createNewDepartment(ActionEvent event) {
        // Creating a stage on which the user will be able to create a new department
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(view.getStage());
        dialog.setTitle("Create New Department");
        dialog.setResizable(false);

        // Defining the elements on the stage
        TextField departmentNameField = new TextField();
        TextArea departmentDescriptionField = new TextArea();
        Button createNewDepartmentBtn = new Button();
        Text topNote = new Text(MAX_DEPARTMENT_NAME_LENGTH + " characters remaining.");
        Text bottomNote = new Text(MAX_DEPARTMENT_DESCRIPTION_LENGTH + " characters remaining.");

        // Configuring the elements
        departmentNameField.setPromptText("Department name");
        departmentDescriptionField.setPromptText("Department description");
        departmentDescriptionField.setWrapText(true);
        createNewDepartmentBtn.textProperty().set("Create department");

        // Defining event handlers for interactions with elements

        // Updating the name character limit note as the user types
        departmentNameField.setOnKeyTyped(e -> {
            int length = departmentNameField.getLength();
            topNote.setText(length > MAX_DEPARTMENT_NAME_LENGTH ?
                    "Your input will be truncated" :
                    MAX_DEPARTMENT_NAME_LENGTH - length + " characters remaining.");
        });

        // Updating the description character limit note as the user types
        departmentDescriptionField.setOnKeyTyped(e -> {
            int length = departmentDescriptionField.getLength();
            bottomNote.setText(length > MAX_DEPARTMENT_DESCRIPTION_LENGTH ?
                    "Your input will be truncated" :
                    MAX_DEPARTMENT_DESCRIPTION_LENGTH - length + " characters remaining.");
        });

        // Handling click on create department button
        createNewDepartmentBtn.setOnAction(e -> {
            if (departmentNameField.getLength() == 0) { // A department must have a name
                WarningPopup.createWarningPopup("No Name Provided", "Department name can not be empty!", dialog);
            } else {
                // Ensuring that the name and description not too long, and creating new department
                String name = departmentNameField.getText().substring(0, Math.min(MAX_DEPARTMENT_NAME_LENGTH, departmentNameField.getLength()));
                String description = departmentDescriptionField.getText().substring(0, Math.min(MAX_DEPARTMENT_DESCRIPTION_LENGTH, departmentDescriptionField.getLength()));
                Department department = new Department(name, description);

                // Checking if a department with the same name exists
                if (departmentsComboBox.getItems().stream().anyMatch(dep -> name.equalsIgnoreCase(dep.getName()))) {
                    WarningPopup.createWarningPopup("Department Already Exists", "A department with this name already exists in this company!", dialog);
                } else {
                    this.model.addDepartment(department);
                    departmentsComboBox.getItems().add(department);
                    this.departmentCountTxt.setText("Number of Departments: " + this.model.getDepartments().size());
                    // Adjusting total company budget
                    this.companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());
                    dialog.close();
                }
            }
        });

        // Creating layout for the scene
        HBox topHbox = new HBox(departmentNameField, topNote);
        topHbox.setSpacing(10);
        HBox bottomHbox = new HBox(createNewDepartmentBtn, bottomNote);
        bottomHbox.setSpacing(10);
        VBox dialogVbox = new VBox(topHbox,
                departmentDescriptionField, bottomHbox);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);

        // Setting the scene on the stage and showing the stage
        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    @FXML
    private void setCompanySalaryBudget(ActionEvent event) {

    }

    // === DEPARTMENT SECTION EVENT HANDLERS ============================================================
    @FXML
    private void selectNewDepartment(ActionEvent event) {
        // Getting the selected department and displaying its details
        Department selectedDepartment = departmentsComboBox.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            // Displaying the selected department's details
            selectedDepartmentName.setText("Selected Department: " + selectedDepartment.getName());
            departmentDescriptionTxt.setText(selectedDepartment.getDescription());
            selectedDepartmentTeamCount.setText("Number of Teams: " + selectedDepartment.getTeams().size());
            selectedDepartmentEmployeeCount.setText("Number of Employees: " + selectedDepartment.getEmployeeCount());
            selectedDepartmentBudget.setText("Department Salary Budget: " + selectedDepartment.getSalaryBudget());
            selectedDepartmentExpenses.setText("Department Salary Expense: " + selectedDepartment.getSalaryExpense());

            // The teams comboBox should only contain the selected department's teams
            teamsComboBox.getItems().clear();
            teamsComboBox.getItems().addAll(selectedDepartment.getTeams());
        } else {    // If selection is empty
            selectedDepartmentName.setText("Selected Department: ");
            departmentDescriptionTxt.setText("");
            selectedDepartmentTeamCount.setText("Number of Teams: ");
            selectedDepartmentEmployeeCount.setText("Number of Employees: ");
            selectedDepartmentBudget.setText("Department Salary Budget: ");
            selectedDepartmentExpenses.setText("Department Salary Expense: ");

            // The teams comboBox should contain no teams if no department is selected
            teamsComboBox.getItems().clear();
        }
    }
    @FXML
    private void editDepartmentDetails(ActionEvent event) {
        Department choice = departmentsComboBox.getSelectionModel().getSelectedItem();

        if (choice != null) {   // A department must be selected for editing
            // Creating a stage on which the user will be able to edit department details
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Edit Department Details");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextField departmentNameField = new TextField();
            TextArea departmentDescriptionField = new TextArea();
            Button setDepartmentNameBtn = new Button();
            Button setDepartmentDescriptionBtn = new Button();
            Text topNote = new Text(MAX_DEPARTMENT_NAME_LENGTH + " characters remaining.");
            Text bottomNote = new Text(MAX_DEPARTMENT_DESCRIPTION_LENGTH + " characters remaining.");

            // Configuring elements
            departmentNameField.setPromptText("New department name");
            departmentDescriptionField.setPromptText("New department description");
            departmentDescriptionField.setWrapText(true);
            setDepartmentNameBtn.textProperty().set("Set new name");
            setDepartmentDescriptionBtn.textProperty().set("Set new description");

            // Defining event handlers for interactions with elements

            // Updating the name character limit note as the user types
            departmentNameField.setOnKeyTyped(e -> {
                int length = departmentNameField.getLength();
                topNote.setText(length > MAX_DEPARTMENT_NAME_LENGTH ?
                        "Your input will be truncated" :
                        MAX_DEPARTMENT_NAME_LENGTH - length + " characters remaining.");
            });

            // Updating the description character limit note as the user types
            departmentDescriptionField.setOnKeyTyped(e -> {
                int length = departmentDescriptionField.getLength();
                bottomNote.setText(length > MAX_DEPARTMENT_DESCRIPTION_LENGTH ?
                        "Your input will be truncated" :
                        MAX_DEPARTMENT_DESCRIPTION_LENGTH - length + " characters remaining.");
            });

            // Updating the name of the department on set department name button click
            setDepartmentNameBtn.setOnAction(e -> {
                // Ensuring that the name is not too long
                String name = departmentNameField.getText().substring(0, Math.min(MAX_DEPARTMENT_NAME_LENGTH, departmentNameField.getLength()));

                // Checking that the name is not empty and that an existing department doesn't have the same name
                if (name.length() == 0) {
                    WarningPopup.createWarningPopup("No Name Provided", "Department name can not be empty!", dialog);
                } else if (departmentsComboBox.getItems().stream().anyMatch(dep -> name.equalsIgnoreCase(dep.getName()))) {
                    WarningPopup.createWarningPopup("Department Already Exists", "A department with this name already exists in this company!", dialog);
                } else {
                    choice.setName(name);
                    // Selecting and re-selecting so that the comboBox updates, which in turn updates the displayed details
                    departmentsComboBox.getSelectionModel().clearSelection();
                    departmentsComboBox.getSelectionModel().select(choice);

                    // Refreshing the employee data table to reflect any changes in this department's name
                    this.employeeRecordsTable.refresh();
                }
            });

            // Updating the description of the department on set department description button click
            setDepartmentDescriptionBtn.setOnAction(e -> {
                String description = departmentDescriptionField.getText().substring(0, Math.min(MAX_DEPARTMENT_DESCRIPTION_LENGTH, departmentDescriptionField.getLength()));
                choice.setDescription(description);
                departmentDescriptionTxt.setText(choice.getDescription());
            });

            // Creating layout for the scene
            HBox topHbox = new HBox(departmentNameField, topNote, setDepartmentNameBtn);
            topHbox.setSpacing(10);
            HBox bottomHbox = new HBox(setDepartmentDescriptionBtn, bottomNote);
            bottomHbox.setSpacing(10);
            VBox dialogVbox = new VBox(topHbox, departmentDescriptionField, bottomHbox);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting a scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        }
        else {  // Cannot edit a department if no department is selected
            WarningPopup.createWarningPopup("No Department Selected", "Select a department from the dropdown first to edit.", this.view.getStage());
        }

    }
    @FXML
    private void deleteDepartment(ActionEvent event) {
        Department choice = departmentsComboBox.getSelectionModel().getSelectedItem();

        if (choice != null) {   // A department must be selected for deletion
            // Creating a stage on which the user will be able to confirm deletion
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Deletion Confirmation");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextFlow confirmationText = new TextFlow();
            Text text1 = new Text("Are you sure you would like to delete the " );
            Text departmentName = new Text(choice.getName());
            Text text2 = new Text(" department? All teams and employees belonging to this department " +
                    "will be deleted / fired. This is an irreversible action.");
            Button deletionConfirmationBtn = new Button();

            // Configuring elements
            departmentName.setStyle("-fx-font-weight: bold");
            confirmationText.getChildren().addAll(text1, departmentName, text2);
            deletionConfirmationBtn.textProperty().set("Delete Department");

            // Defining event handler for click on delete confirmation
            deletionConfirmationBtn.setOnAction(e -> {
                departmentsComboBox.getItems().remove(choice);
                this.model.removeDepartment(choice);

                // Adjusting the company's team count
                this.model.incrementTeamCount(-choice.getTeams().size());
                // Adjusting the company's employee count, and removing the employees from the employee table
                for (Team team : choice.getTeams()) {
                    this.model.incrementEmployeeCount(-team.getEmployees().size());
                    for (Employee employee : team.getEmployees()) {
                        employeeRecordsTable.getItems().remove(employee);
                    }
                }
                // Adjusting total company budget
                this.companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());

                // Updating the company details UI to reflect the changes resulting from the department deletion
                updateCompanyDetailsUI();
                dialog.close();
            });

            // Creating layout for the scene
            VBox dialogVbox = new VBox(confirmationText, deletionConfirmationBtn);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting a scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox, 500, 120);
            dialog.setScene(dialogScene);
            dialog.show();
        } else {    // Cannot delete a department if no department is selected
            WarningPopup.createWarningPopup("No Department Selected", "Select a department from the " +
                    "dropdown first to delete.", this.view.getStage());
        }
    }

    // === TEAM SECTION EVENT HANDLERS ===================================================================
    @FXML
    private void createNewTeam(ActionEvent event) {
        // Creating a stage on which the user will be able to create a new team
        Department choice = departmentsComboBox.getSelectionModel().getSelectedItem();

        if (choice == null) {
            WarningPopup.createWarningPopup("No Department selected", "Cannot add a team without a specified department", this.view.getStage());
        } else {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Create New Team");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextField teamNameField = new TextField();
            TextArea teamDescriptionField = new TextArea();
            Button createNewTeamBtn = new Button();
            Text topNote = new Text(MAX_TEAM_NAME_LENGTH + " characters remaining.");
            Text bottomNote = new Text(MAX_TEAM_DESCRIPTION_LENGTH + " characters remaining.");

            // Configuring the elements
            teamNameField.setPromptText("Team name");
            teamDescriptionField.setPromptText("Team description");
            teamDescriptionField.setWrapText(true);
            createNewTeamBtn.textProperty().set("Create Team");

            // Defining event handlers for interactions with elements

            // Updating the name character limit note as the user types
            teamNameField.setOnKeyTyped(e -> {
                int length = teamNameField.getLength();
                topNote.setText(length > MAX_TEAM_NAME_LENGTH ?
                        "Your input will be truncated" :
                        MAX_TEAM_NAME_LENGTH - length + " characters remaining.");
            });

            // Updating the description character limit note as the user types
            teamDescriptionField.setOnKeyTyped(e -> {
                int length = teamDescriptionField.getLength();
                bottomNote.setText(length > MAX_TEAM_DESCRIPTION_LENGTH ?
                        "Your input will be truncated" :
                        MAX_TEAM_DESCRIPTION_LENGTH - length + " characters remaining.");
            });

            // Handling click on create team button
            createNewTeamBtn.setOnAction(e -> {
                // checking if the user has selected a department to add a team to or not

                if (teamNameField.getLength() == 0) { // A team must have a name
                    WarningPopup.createWarningPopup("No Name Provided", "Team name can not be empty!", dialog);
                } else {
                    // Ensuring that the name and description not too long, and creating new department
                    String name = teamNameField.getText().substring(0, Math.min(MAX_TEAM_NAME_LENGTH, teamNameField.getLength()));
                    String description = teamDescriptionField.getText().substring(0, Math.min(MAX_TEAM_DESCRIPTION_LENGTH, teamDescriptionField.getLength()));
                    Team team = new Team(name, description);

                    // Checking if a team with the same name exists
                    if (teamsComboBox.getItems().stream().anyMatch(t -> name.equalsIgnoreCase(t.getName()))) {
                        WarningPopup.createWarningPopup("Team Already Exists", "A team with this name already exists in this company!", dialog);
                    } else {
                        choice.addTeam(team);
                        teamsComboBox.getItems().add(team);
                        this.model.incrementTeamCount(1);
                        this.teamCountTxt.setText("Number of Teams: " + model.getTeamCount());
                        this.selectedDepartmentTeamCount.setText("Number of Teams: " + choice.getTeams().size());
                        // Adjusting total company and specific department budget
                        this.companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());
                        this.selectedDepartmentExpenses.setText("Department Salary Expense: " + choice.getSalaryExpense());
                        dialog.close();
                    }
                }

            });

            // Creating layout for the scene
            HBox topHbox = new HBox(teamNameField, topNote);
            topHbox.setSpacing(10);
            HBox bottomHbox = new HBox(createNewTeamBtn, bottomNote);
            bottomHbox.setSpacing(10);
            VBox dialogVbox = new VBox(topHbox,
                    teamDescriptionField, bottomHbox);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting the scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }
    @FXML
    private void selectNewTeam(ActionEvent event) {
        // Getting the selected team and displaying its details
        Team choice = teamsComboBox.getSelectionModel().getSelectedItem();
        if (choice != null) {
            selectedTeamName.setText("Selected Team: " + choice.getName());
            teamDescriptionTxt.setText(choice.getDescription());
            selectedTeamEmployeeCount.setText("Number of employees: " + choice.getEmployees().size());
            selectedTeamBudget.setText("Team Salary Budget: " + choice.getSalaryBudget());
            selectedTeamExpenses.setText("Team Salary Expense: " + choice.getSalaryExpense());
        } else {    // If selection is empty
            selectedTeamName.setText("Selected Team: ");
            teamDescriptionTxt.setText("");
            selectedTeamEmployeeCount.setText("Number of Employees: ");
            selectedTeamBudget.setText("Team Salary Budget: ");
            selectedTeamExpenses.setText("Team Salary Expense: ");
        }
    }
    @FXML
    private void editTeamDetails(ActionEvent event) {
        // Getting selected team and department
        Team selectedTeam = teamsComboBox.getSelectionModel().getSelectedItem();
        Department selectedDepartment = departmentsComboBox.getSelectionModel().getSelectedItem();

        if (selectedTeam == null) { // Cannot edit a team if no team is selected
            WarningPopup.createWarningPopup("No Team Selected", "Select a team from the dropdown first to edit.", this.view.getStage());
        }
        else {
            // Creating a stage on which the user will be able to edit team details
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Edit Team Details");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextField teamNameField = new TextField();
            TextArea teamDescriptionField = new TextArea();
            Button setTeamNameBtn = new Button();
            Button setTeamDescriptionBtn = new Button();
            Text topNote = new Text(MAX_TEAM_NAME_LENGTH + " characters remaining.");
            Text bottomNote = new Text(MAX_TEAM_DESCRIPTION_LENGTH + " characters remaining.");

            // Configuring elements
            teamNameField.setPromptText("New team name");
            teamDescriptionField.setPromptText("New team description");
            teamDescriptionField.setWrapText(true);
            setTeamNameBtn.textProperty().set("Set new name");
            setTeamDescriptionBtn.textProperty().set("Set new description");

            // Defining event handlers for interactions with elements

            // Updating the name character limit note as the user types
            teamNameField.setOnKeyTyped(e -> {
                int length = teamNameField.getLength();
                topNote.setText(length > MAX_TEAM_NAME_LENGTH ?
                        "Your input will be truncated" :
                        MAX_TEAM_NAME_LENGTH - length + " characters remaining.");
            });

            // Updating the description character limit note as the user types
            teamDescriptionField.setOnKeyTyped(e -> {
                int length = teamDescriptionField.getLength();
                bottomNote.setText(length > MAX_TEAM_DESCRIPTION_LENGTH ?
                        "Your input will be truncated" :
                        MAX_TEAM_DESCRIPTION_LENGTH - length + " characters remaining.");
            });

            // Updating the name of the team on set team name button click
            setTeamNameBtn.setOnAction(e -> {
                // Ensuring that the name is not too long
                String name = teamNameField.getText().substring(0, Math.min(MAX_TEAM_NAME_LENGTH, teamNameField.getLength()));

                // Checking that the name is not empty
                // Also checking that an existing team in the same department does not have the same name.
                if (name.length() == 0) {
                    WarningPopup.createWarningPopup("No Name Provided", "Team name can not be empty!", dialog);
                } else if (selectedDepartment.getTeams().stream().anyMatch(t -> name.equalsIgnoreCase(t.getName()))) {
                    WarningPopup.createWarningPopup("Team Already Exists in Department", "A team with this name already exists in the same department!", dialog);
                } else {
                    selectedTeam.setName(name);
                    // Selecting and re-selecting so that the comboBox updates, which in turn updates the displayed details
                    teamsComboBox.getSelectionModel().clearSelection();
                    teamsComboBox.getSelectionModel().select(selectedTeam);

                    // Refreshing the employee data table to reflect any changes in this team's name
                    this.employeeRecordsTable.refresh();
                }
            });

            // Updating the description of the team on set team description button click
            setTeamDescriptionBtn.setOnAction(e -> {
                String description = teamDescriptionField.getText().substring(0, Math.min(MAX_TEAM_DESCRIPTION_LENGTH, teamDescriptionField.getLength()));
                selectedTeam.setDescription(description);
                teamDescriptionTxt.setText(selectedTeam.getDescription());
            });

            // Creating layout for the scene
            HBox topHbox = new HBox(teamNameField, topNote, setTeamNameBtn);
            topHbox.setSpacing(10);
            HBox bottomHbox = new HBox(setTeamDescriptionBtn, bottomNote);
            bottomHbox.setSpacing(10);
            VBox dialogVbox = new VBox(topHbox, teamDescriptionField, bottomHbox);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting a scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        }

    }
    @FXML
    private void deleteTeam(ActionEvent event) {
        // A department must be selected to select a team to delete
        Department selectedDepartment = departmentsComboBox.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            Team choice = teamsComboBox.getSelectionModel().getSelectedItem();
            if (choice != null) {   // A team must be selected for deletion
                // Creating a stage on which the user will be able to confirm deletion
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(view.getStage());
                dialog.setTitle("Deletion Confirmation");
                dialog.setResizable(false);

                // Defining the elements on the stage
                TextFlow confirmationText = new TextFlow();
                Text text1 = new Text("Are you sure you would like to delete the ");
                Text teamName = new Text(choice.getName());
                Text text2 = new Text(" team? All employees belonging to this team " +
                        "will be fired. This is an irreversible action.");
                Button deletionConfirmationBtn = new Button();

                // Configuring elements
                teamName.setStyle("-fx-font-weight: bold");
                confirmationText.getChildren().addAll(text1, teamName, text2);
                deletionConfirmationBtn.textProperty().set("Delete Team");

                // Defining event handler for click on delete confirmation
                deletionConfirmationBtn.setOnAction(e -> {
                    teamsComboBox.getItems().remove(choice);
                    selectedDepartment.removeTeam(choice);

                    // Adjusting the company's team count
                    this.model.incrementTeamCount(-1);
                    // Adjusting the department's team count
                    this.selectedDepartmentTeamCount.setText("Number of Teams: " + selectedDepartment.getTeams().size());
                    selectedDepartment.incrementEmployeeCount(-choice.getEmployees().size());
                    this.selectedDepartmentEmployeeCount.setText("Number of Employees: " + selectedDepartment.getEmployeeCount());
                    // Adjusting the company's employee count, and removing the employees from the employee table
                    this.model.incrementEmployeeCount(-choice.getEmployees().size());
                    for (Employee employee : choice.getEmployees()) {
                        employeeRecordsTable.getItems().remove(employee);
                    }
                    // Adjusting total company and specific department budget
                    this.companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());
                    this.selectedDepartmentExpenses.setText("Department Salary Expense: " + selectedDepartment.getSalaryExpense());
                    // Updating the company details UI to reflect the changes resulting from the department deletion
                    updateCompanyDetailsUI();
                    dialog.close();
                });

                // Creating layout for the scene
                VBox dialogVbox = new VBox(confirmationText, deletionConfirmationBtn);
                dialogVbox.setPadding(new Insets(20, 20, 20, 20));
                dialogVbox.setSpacing(10);

                // Setting a scene on the stage and showing the stage
                Scene dialogScene = new Scene(dialogVbox, 500, 100);
                dialog.setScene(dialogScene);
                dialog.show();
            } else {    // Cannot delete a team if no team is selected
                WarningPopup.createWarningPopup("No Team Selected", "Select a team from the " +
                        "dropdown first to delete.", this.view.getStage());
            }
        }else {// Cannot delete a team from a department if no department is selected
            WarningPopup.createWarningPopup("No Department Selected", "Select a department from the " +
                    "dropdown first to choose a team to delete.", this.view.getStage());
        }
    }
    @FXML
    private void hireNewEmployee(ActionEvent event) {
        Team choice = teamsComboBox.getSelectionModel().getSelectedItem();
        // checking if the user has selected a team to add an employee to or not
        if (choice == null) {
            WarningPopup.createWarningPopup("No Team selected", "Cannot add a employee without a specified team", this.view.getStage());
        } else {
            // Creating a stage on which the user will be able to hire a new employee
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Create New Employee");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextField employeeFirstNameField = new TextField();
            TextField employeeLastNameField = new TextField();
            TextField employeeSalaryField = new TextField();
            ToggleGroup employeeGenderGroup = new ToggleGroup();
            TextField employeeAgeField = new TextField();
            Button hireNewEmployeeBtn = new Button();
            Text firstNameNote = new Text(MAX_EMPLOYEE_FIRST_NAME_LENGTH + " characters remaining.");
            Text lastNameNote = new Text(MAX_EMPLOYEE_LAST_NAME_LENGTH + " characters remaining.");
            Text salaryNote = new Text(MAX_EMPLOYEE_SALARY_LENGTH + " characters remaining.");
            Text ageNote = new Text(MAX_EMPLOYEE_AGE_LENGTH + " characters remaining.");

            // Configuring the elements
            employeeFirstNameField.setPromptText("Employee first name");
            employeeLastNameField.setPromptText("Employee last name");
            employeeSalaryField.setPromptText("Employee salary");
            employeeAgeField.setPromptText("Employee age");
            hireNewEmployeeBtn.textProperty().set("Hire Employee");

            // Defining event handlers for interactions with elements

            // Updating the first name character limit note as the user types
            employeeFirstNameField.setOnKeyTyped(e -> {
                int length = employeeFirstNameField.getLength();
                firstNameNote.setText(length > MAX_EMPLOYEE_FIRST_NAME_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_FIRST_NAME_LENGTH - length + " characters remaining.");
            });

            // Updating the last name character limit note as the user types
            employeeLastNameField.setOnKeyTyped(e -> {
                int length = employeeLastNameField.getLength();
                lastNameNote.setText(length > MAX_EMPLOYEE_LAST_NAME_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_LAST_NAME_LENGTH - length + " characters remaining.");
            });

            // Updating the salary character limit note as the user types
            employeeSalaryField.setOnKeyTyped(e -> {
                int length = employeeSalaryField.getLength();
                salaryNote.setText(length > MAX_EMPLOYEE_SALARY_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_SALARY_LENGTH - length + " characters remaining.");
            });

            // Updating the age character limit note as the user types
            employeeAgeField.setOnKeyTyped(e -> {
                int length = employeeAgeField.getLength();
                ageNote.setText(length > MAX_EMPLOYEE_AGE_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_AGE_LENGTH - length + " characters remaining.");
            });

            // Setting up gender buttons
            RadioButton employeeGenderMaleButton = new RadioButton("Male");
            employeeGenderMaleButton.setUserData("Male");
            employeeGenderMaleButton.setToggleGroup(employeeGenderGroup);
            employeeGenderMaleButton.setSelected(true);

            RadioButton employeeGenderFemaleButton = new RadioButton("Female");
            employeeGenderFemaleButton.setUserData("Female");
            employeeGenderFemaleButton.setToggleGroup(employeeGenderGroup);

            RadioButton employeeGenderTransButton = new RadioButton("Transgender");
            employeeGenderTransButton.setUserData("Transgender");
            employeeGenderTransButton.setToggleGroup(employeeGenderGroup);

            RadioButton employeeGenderNonBinaryButton = new RadioButton("Non-Binary");
            employeeGenderNonBinaryButton.setUserData("Non-Binary");
            employeeGenderNonBinaryButton.setToggleGroup(employeeGenderGroup);

            RadioButton employeeGenderNoResponseButton = new RadioButton("Prefer not to answer");
            employeeGenderNoResponseButton.setUserData("Prefer not to answer");
            employeeGenderNoResponseButton.setToggleGroup(employeeGenderGroup);

            // Handling click on hire new employee button
            hireNewEmployeeBtn.setOnAction(e -> {
                // Checking if inputs are not null
                if (employeeFirstNameField.getLength() == 0 || employeeLastNameField.getLength() == 0 || employeeSalaryField.getLength() == 0 || employeeAgeField.getLength() == 0 || employeeGenderGroup.getSelectedToggle() == null) {
                    WarningPopup.createWarningPopup("Complete Details Not Provided", "Employee details can not be empty!", dialog);
                }
                else {
                    // Ensuring that the employee details not too long, and creating new employee

                    // Checking for appropriate input types
                    try{
                        String firstName = employeeFirstNameField.getText().substring(0, Math.min(MAX_EMPLOYEE_FIRST_NAME_LENGTH, employeeFirstNameField.getLength()));
                        String lastName = employeeLastNameField.getText().substring(0, Math.min(MAX_EMPLOYEE_LAST_NAME_LENGTH, employeeLastNameField.getLength()));
                        int age = Integer.parseInt(employeeAgeField.getText().substring(0, Math.min(MAX_EMPLOYEE_AGE_LENGTH, employeeAgeField.getLength())));
                        String salaryString = employeeSalaryField.getText().substring(0, Math.min(MAX_EMPLOYEE_SALARY_LENGTH, employeeSalaryField.getLength()));
                        double salary = Math.round(Double.parseDouble(salaryString) * 100.0) / 100.0;
                        String gender = employeeGenderGroup.getSelectedToggle().getUserData().toString();
                        Employee employee = new Employee(firstName, lastName, salary, gender, age, EMPLOYEE_ID, this.teamsComboBox.getSelectionModel().getSelectedItem(), this.departmentsComboBox.getSelectionModel().getSelectedItem());
                        EMPLOYEE_ID ++;
                        choice.addEmployee(employee);
                        this.model.incrementEmployeeCount(1);
                        departmentsComboBox.getSelectionModel().getSelectedItem().incrementEmployeeCount(1);
                        this.selectedDepartmentEmployeeCount.setText("Number of Employees: " + departmentsComboBox.getSelectionModel().getSelectedItem().getEmployeeCount());
                        this.selectedTeamEmployeeCount.setText("Number of Employees: " + choice.getEmployees().size());
                        this.employeeCountTxt.setText("Number of Employees: " + model.getEmployeeCount());
                        this.employeeRecordsTable.getItems().add(employee); // Adds the employee to the employee records view
                        // Adjusting total company, specific department and team budget
                        this.companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());
                        this.selectedDepartmentExpenses.setText("Department Salary Expense: " + this.departmentsComboBox.getSelectionModel().getSelectedItem().getSalaryExpense());
                        this.selectedTeamExpenses.setText("Team Salary Expense: " + this.teamsComboBox.getSelectionModel().getSelectedItem().getSalaryExpense());
                        dialog.close();
                    } catch (NumberFormatException ex) {
                        WarningPopup.createWarningPopup("Wrong Input Types", "Employee age or salary cannot be a String!", dialog);
                    }

                }

            });

            // Creating layout for the scene
            HBox firstNameHbox = new HBox(new Text("First Name:"), employeeFirstNameField, firstNameNote);
            firstNameHbox.setSpacing(10);
            HBox lastNameHbox = new HBox(new Text("Last Name:"), employeeLastNameField, lastNameNote);
            lastNameHbox.setSpacing(10);
            HBox salaryHbox = new HBox(new Text("Salary:"), employeeSalaryField, salaryNote);
            salaryHbox.setSpacing(10);
            HBox genderbox = new HBox(new Text("Gender:"));
            HBox genderHbox = new HBox(employeeGenderMaleButton, employeeGenderFemaleButton, employeeGenderTransButton, employeeGenderNonBinaryButton, employeeGenderNoResponseButton);
            genderHbox.setSpacing(10);
            HBox ageHbox = new HBox(new Text("Age:"), employeeAgeField, ageNote);
            ageHbox.setSpacing(10);
            HBox bottomHbox = new HBox(hireNewEmployeeBtn);
            bottomHbox.setSpacing(10);
            VBox dialogVbox = new VBox(firstNameHbox, lastNameHbox, salaryHbox, genderbox, genderHbox, ageHbox, bottomHbox);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting the scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }

    // === EMPLOYEE SECTION EVENT HANDLERS ===============================================================
    @FXML
    private void editEmployeeDetails(ActionEvent event) {
        Employee selected_employee = employeeRecordsTable.getSelectionModel().getSelectedItem();
        // Checking if the user has selected an employee or not
        if (selected_employee == null) {
            WarningPopup.createWarningPopup("No Employee Selected", "An employee must first be selected from the employee records table.", this.view.getStage());
        } else {
            // Creating a stage on which the user will be able to edit the selected employee's details
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Edit Employee Details");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextField employeeFirstNameField = new TextField(selected_employee.getFirstName());
            TextField employeeLastNameField = new TextField(selected_employee.getLastName());
            TextField employeeSalaryField = new TextField(String.valueOf(selected_employee.getSalary()));
            ToggleGroup employeeGenderGroup = new ToggleGroup();
            TextField employeeAgeField = new TextField(String.valueOf(selected_employee.getAge()));
            Button changeEmployeeDetailsBtn = new Button("Change Details");
            Text firstNameNote = new Text(MAX_EMPLOYEE_FIRST_NAME_LENGTH + " characters remaining.");
            Text lastNameNote = new Text(MAX_EMPLOYEE_LAST_NAME_LENGTH + " characters remaining.");
            Text salaryNote = new Text(MAX_EMPLOYEE_SALARY_LENGTH + " characters remaining.");
            Text ageNote = new Text(MAX_EMPLOYEE_AGE_LENGTH + " characters remaining.");

            // Configuring the elements
            employeeFirstNameField.setPromptText("Employee first name");
            employeeLastNameField.setPromptText("Employee last name");
            employeeSalaryField.setPromptText("Employee salary");
            employeeAgeField.setPromptText("Employee age");

            // Setting up gender radio buttons
            RadioButton employeeGenderMaleButton = new RadioButton("Male");
            employeeGenderMaleButton.setUserData("Male");
            employeeGenderMaleButton.setToggleGroup(employeeGenderGroup);

            RadioButton employeeGenderFemaleButton = new RadioButton("Female");
            employeeGenderFemaleButton.setUserData("Female");
            employeeGenderFemaleButton.setToggleGroup(employeeGenderGroup);

            RadioButton employeeGenderTransButton = new RadioButton("Transgender");
            employeeGenderTransButton.setUserData("Transgender");
            employeeGenderTransButton.setToggleGroup(employeeGenderGroup);

            RadioButton employeeGenderNonBinaryButton = new RadioButton("Non-Binary");
            employeeGenderNonBinaryButton.setUserData("Non-Binary");
            employeeGenderNonBinaryButton.setToggleGroup(employeeGenderGroup);

            RadioButton employeeGenderNoResponseButton = new RadioButton("Prefer not to answer");
            employeeGenderNoResponseButton.setUserData("Prefer not to answer");
            employeeGenderNoResponseButton.setToggleGroup(employeeGenderGroup);

            // Selecting the selected employee's specified gender
            switch (selected_employee.getGender()) {
                case "Male" -> employeeGenderMaleButton.selectedProperty().set(true);
                case "Female" -> employeeGenderFemaleButton.selectedProperty().set(true);
                case "Transgender" -> employeeGenderTransButton.selectedProperty().set(true);
                case "Non-Binary" -> employeeGenderNonBinaryButton.selectedProperty().set(true);
                case "Prefer not to answer" -> employeeGenderNoResponseButton.selectedProperty().set(true);
            }

            // Defining event handlers for interactions with elements

            // Updating the first name character limit note as the user types
            employeeFirstNameField.setOnKeyTyped(e -> {
                int length = employeeFirstNameField.getLength();
                firstNameNote.setText(length > MAX_EMPLOYEE_FIRST_NAME_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_FIRST_NAME_LENGTH - length + " characters remaining.");
            });

            // Updating the last name character limit note as the user types
            employeeLastNameField.setOnKeyTyped(e -> {
                int length = employeeLastNameField.getLength();
                lastNameNote.setText(length > MAX_EMPLOYEE_LAST_NAME_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_LAST_NAME_LENGTH - length + " characters remaining.");
            });

            // Updating the salary character limit note as the user types
            employeeSalaryField.setOnKeyTyped(e -> {
                int length = employeeSalaryField.getLength();
                salaryNote.setText(length > MAX_EMPLOYEE_SALARY_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_SALARY_LENGTH - length + " characters remaining.");
            });

            // Updating the age character limit note as the user types
            employeeAgeField.setOnKeyTyped(e -> {
                int length = employeeAgeField.getLength();
                ageNote.setText(length > MAX_EMPLOYEE_AGE_LENGTH ?
                        "Your input will be truncated" :
                        MAX_EMPLOYEE_AGE_LENGTH - length + " characters remaining.");
            });

            // Handling click on change employee details button
            changeEmployeeDetailsBtn.setOnAction(e -> {
                // Checking if inputs are not null
                if (employeeFirstNameField.getLength() == 0 || employeeLastNameField.getLength() == 0 || employeeSalaryField.getLength() == 0 || employeeAgeField.getLength() == 0 || employeeGenderGroup.getSelectedToggle() == null){
                    WarningPopup.createWarningPopup("Complete Details Not Provided", "Employee details can not be empty!", dialog);
                } else {
                    // Ensuring that the employee details not too long, and creating new employee

                    // Checking for appropriate input types
                    try{
                        String firstName = employeeFirstNameField.getText().substring(0, Math.min(MAX_EMPLOYEE_FIRST_NAME_LENGTH, employeeFirstNameField.getLength()));
                        String lastName = employeeLastNameField.getText().substring(0, Math.min(MAX_EMPLOYEE_LAST_NAME_LENGTH, employeeLastNameField.getLength()));
                        int age = Integer.parseInt(employeeAgeField.getText().substring(0, Math.min(MAX_EMPLOYEE_AGE_LENGTH, employeeAgeField.getLength())));
                        String salaryString = employeeSalaryField.getText().substring(0, Math.min(MAX_EMPLOYEE_SALARY_LENGTH, employeeSalaryField.getLength()));
                        double salary = Math.round(Double.parseDouble(salaryString) * 100.0) / 100.0;
                        String gender = employeeGenderGroup.getSelectedToggle().getUserData().toString();
                        selected_employee.setFirstName(firstName);
                        selected_employee.setLastName(lastName);
                        selected_employee.setAge(age);
                        selected_employee.setSalary(salary);
                        selected_employee.setGender(gender);

                        // Adjusting total company budget
                        this.companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());
                        Department selected_department = departmentsComboBox.getSelectionModel().getSelectedItem();
                        if (selected_department == selected_employee.getDepartment()) {
                            this.selectedDepartmentEmployeeCount.setText("Number of Employees: " + selected_department.getEmployeeCount());
                            // Adjusting department budget
                            this.selectedDepartmentExpenses.setText("Department Salary Expense: " + this.departmentsComboBox.getSelectionModel().getSelectedItem().getSalaryExpense());
                        }
                        Team selected_team = teamsComboBox.getSelectionModel().getSelectedItem();
                        if (selected_team == selected_employee.getTeam()) {
                            this.selectedTeamEmployeeCount.setText("Number of Employees: " + selected_team.getEmployees().size());
                            // Adjusting team budget
                            this.selectedTeamExpenses.setText("Team Salary Expense: " + this.teamsComboBox.getSelectionModel().getSelectedItem().getSalaryExpense());
                        }
                        employeeRecordsTable.refresh(); // Refreshing the table to display new details
                        dialog.close();
                    } catch (NumberFormatException ex) {
                        WarningPopup.createWarningPopup("Wrong Input Types", "Employee age and salary cannot be a String!", dialog);
                    }
                }
            });

            // Creating layout for the scene
            HBox firstNameHbox = new HBox(new Text("First Name:"), employeeFirstNameField, firstNameNote);
            firstNameHbox.setSpacing(10);
            HBox lastNameHbox = new HBox(new Text("Last Name:"), employeeLastNameField, lastNameNote);
            lastNameHbox.setSpacing(10);
            HBox salaryHbox = new HBox(new Text("Salary:"), employeeSalaryField, salaryNote);
            salaryHbox.setSpacing(10);
            HBox genderbox = new HBox(new Text("Gender:"));
            HBox genderHbox = new HBox(employeeGenderMaleButton, employeeGenderFemaleButton, employeeGenderTransButton, employeeGenderNonBinaryButton, employeeGenderNoResponseButton);
            genderHbox.setSpacing(10);
            HBox ageHbox = new HBox(new Text("Age:"), employeeAgeField, ageNote);
            ageHbox.setSpacing(10);
            HBox bottomHbox = new HBox(changeEmployeeDetailsBtn);
            bottomHbox.setSpacing(10);
            VBox dialogVbox = new VBox(firstNameHbox, lastNameHbox, salaryHbox, genderbox, genderHbox, ageHbox, bottomHbox);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting the scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }
    @FXML
    private void changeEmployeeTeam(ActionEvent event) {
        Employee selected_employee = employeeRecordsTable.getSelectionModel().getSelectedItem();
        Team selected_team = teamsComboBox.getSelectionModel().getSelectedItem();
        // Checking if the user has selected an employee and team or not
        if (selected_employee == null) {
            WarningPopup.createWarningPopup("No Employee Selected", "An employee must first be selected from the employee records table.", this.view.getStage());
        } else if (selected_team == null) {
            WarningPopup.createWarningPopup("No Team Selected", "A team must first be selected in the teams dashboard.", this.view.getStage());
        } else {
            // Creating a stage on which the user will be able to confirm the team change
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Team Change Confirmation");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextFlow confirmationText = new TextFlow();
            Text text1 = new Text("Are you sure you would like to change the employee's team to " );
            Text departmentName = new Text(selected_team.getName());
            Text text2 = new Text("?");
            Button confirmationBtn = new Button("Change Team");

            // Configuring elements
            departmentName.setStyle("-fx-font-weight: bold");
            confirmationText.getChildren().addAll(text1, departmentName, text2);

            // Defining event handler for click on confirmation button
            confirmationBtn.setOnAction(e -> {
                // Removing employee from current team
                Team current_team = selected_employee.getTeam();
                current_team.removeEmployee(selected_employee);
                selected_employee.getDepartment().incrementEmployeeCount(-1);

                // Adding employee to new team
                Department selected_department = departmentsComboBox.getSelectionModel().getSelectedItem();
                selected_team.addEmployee(selected_employee);
                selected_employee.setTeam(selected_team);
                selected_employee.setDepartment(selected_department);
                selected_department.incrementEmployeeCount(1);

                // Updating employee counts
                this.selectedDepartmentEmployeeCount.setText("Number of Employees: " + selected_department.getEmployeeCount());
                this.selectedTeamEmployeeCount.setText("Number of Employees: " + selected_team.getEmployees().size());

                employeeRecordsTable.refresh(); // Updating table with changes
                dialog.close();
            });

            // Creating layout for the scene
            VBox dialogVbox = new VBox(confirmationText, confirmationBtn);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting a scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox, 500, 100);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }
    @FXML
    private void fireSelectedEmployee(ActionEvent event) {
        Employee selected_employee = employeeRecordsTable.getSelectionModel().getSelectedItem();
        // checking if the user has selected an employee to fire or not
        if (selected_employee == null) {
            WarningPopup.createWarningPopup("No Employee selected", "An employee must first be selected from the employee records table.", this.view.getStage());
        } else {
            // Creating a stage on which the user will be able to fire employee
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Fire Employee");
            dialog.setResizable(false);

            // Defining the elements on the stage
            TextFlow confirmationText = new TextFlow();
            Text text1 = new Text("Are you sure you would like to fire the " );
            Text employeeName = new Text(selected_employee.getFullName());
            Text text2 = new Text(" employee? This is an irreversible action.");
            Button firingConfirmationBtn = new Button();

            // Configuring the elements
            employeeName.setStyle("-fx-font-weight: bold");
            confirmationText.getChildren().addAll(text1, employeeName, text2);
            firingConfirmationBtn.textProperty().set("Fire Employee");

            // Defining event handlers for click on firing confirmation
            firingConfirmationBtn.setOnAction(e -> {
                // Removing employee from team, department and employee table
                selected_employee.getTeam().removeEmployee(selected_employee);
                selected_employee.getDepartment().incrementEmployeeCount(-1);
                this.model.incrementEmployeeCount(-1);
                employeeRecordsTable.getItems().remove(selected_employee);

                // Adjusting total company budget
                this.companySalaryExpenseTxt.setText("Total Salary Expense: " + model.getSalaryExpense());
                // Updating employee counts
                this.employeeCountTxt.setText("Number of Employees: " + this.model.getEmployeeCount());
                Department selected_department = departmentsComboBox.getSelectionModel().getSelectedItem();
                if (selected_department == selected_employee.getDepartment()) {
                    this.selectedDepartmentEmployeeCount.setText("Number of Employees: " + selected_department.getEmployeeCount());
                    // Adjusting department budget
                    this.selectedDepartmentExpenses.setText("Department Salary Expense: " + this.departmentsComboBox.getSelectionModel().getSelectedItem().getSalaryExpense());
                }
                Team selected_team = teamsComboBox.getSelectionModel().getSelectedItem();
                if (selected_team == selected_employee.getTeam()) {
                    this.selectedTeamEmployeeCount.setText("Number of Employees: " + selected_team.getEmployees().size());
                    // Adjusting team budget
                    this.selectedTeamExpenses.setText("Team Salary Expense: " + this.teamsComboBox.getSelectionModel().getSelectedItem().getSalaryExpense());
                }

                // Updating the company details UI and employee table to reflect the changes
                updateCompanyDetailsUI();
                employeeRecordsTable.refresh();
                dialog.close();
            });

            // Creating layout for the scene
            VBox dialogVbox = new VBox(confirmationText, firingConfirmationBtn);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);

            // Setting the scene on the stage and showing the stage
            Scene dialogScene = new Scene(dialogVbox, 500, 120);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }
}