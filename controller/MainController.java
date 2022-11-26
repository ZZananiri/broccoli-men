package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CompanyModel;
import models.Department;
import models.Team;
import views.MainView;
import views.WarningPopup;
import java.util.ArrayList;

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
        Department choice = departmentsComboBox.getSelectionModel().getSelectedItem();
        if (choice != null) {
            selectedDepartmentName.setText("Selected Department: " + choice.getName());
            departmentDescriptionTxt.setText(choice.getDescription());
            selectedDepartmentTeamCount.setText("Number of Teams: " + choice.getTeams().size());
            selectedDepartmentEmployeeCount.setText("Number of Employees: " + choice.getEmployeeCount());
            selectedDepartmentBudget.setText("Department Salary Budget: " + choice.getSalaryBudget());
            selectedDepartmentExpenses.setText("Department Salary Expense: " + choice.getSalaryExpense());
        } else {    // If selection is empty
            selectedDepartmentName.setText("Selected Department: ");
            departmentDescriptionTxt.setText("");
            selectedDepartmentTeamCount.setText("Number of Teams: ");
            selectedDepartmentEmployeeCount.setText("Number of Employees: ");
            selectedDepartmentBudget.setText("Department Salary Budget: ");
            selectedDepartmentExpenses.setText("Department Salary Expense: ");
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
            selectedDepartmentName.setText("Selected Team: ");
            departmentDescriptionTxt.setText("");
            selectedDepartmentTeamCount.setText("Number of Teams: ");
            selectedDepartmentEmployeeCount.setText("Number of Employees: ");
            selectedDepartmentBudget.setText("Team Salary Budget: ");
            selectedDepartmentExpenses.setText("Team Salary Expense: ");
        }
    }

    // === EMPLOYEE SECTION EVENT HANDLERS ===============================================================

}
