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
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.CompanyModel;
import models.Department;
import views.MainView;
import views.WarningPopup;

public class MainController {
    CompanyModel model;
    MainView view;

    public MainController(CompanyModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    // COMPANY SECTION UI ELEMENTS
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

    @FXML
    private void displaySelectedDepartment(ActionEvent event) {
        if (!(departmentsComboBox.getSelectionModel().getSelectedItem() == null)) {
            Department choice = departmentsComboBox.getSelectionModel().getSelectedItem();
            selectedDepartmentName.setText("Selected Department: " + choice.getName());
            departmentDescriptionTxt.setText(choice.getDescription());
            selectedDepartmentTeamCount.setText("Number of Teams: " + choice.getTeams().size());
            selectedDepartmentEmployeeCount.setText("Number of Employees: " + choice.getEmployees().size());
            selectedDepartmentBudget.setText("Department Salary Budget: " + choice.getBudget());
            selectedDepartmentExpenses.setText("Department Salary Expense: " + choice.getExpense());
        } else {
            WarningPopup.createWarningPopup("No Department Selected", "Select a department from the dropdown first to view.", this.view.getStage());
        }
    }

    @FXML
    private void editCompanyDetails(ActionEvent event) {
        int max_name_length = 16;
        int max_description_length = 270;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(view.getStage());
        dialog.setTitle("Edit Company Details");
        dialog.setResizable(false);
        TextField companyNameField = new TextField();
        TextArea companyDescriptionField = new TextArea();
        Button setCompanyNameBtn = new Button();
        Button setCompanyDescriptionBtn = new Button();
        Text topNote = new Text(max_name_length + " characters remaining.");
        Text bottomNote = new Text(max_description_length + " characters remaining.");
        companyNameField.setPromptText("New company name");
        companyNameField.setOnKeyTyped(e -> {
            int length = companyNameField.getLength();
            topNote.setText(length > max_name_length ?
                    "Your input will be truncated" :
                    max_name_length - length + " characters remaining.");
        });
        companyDescriptionField.setPromptText("New company description");
        companyDescriptionField.setWrapText(true);
        companyDescriptionField.setOnKeyTyped(e -> {
            int length = companyDescriptionField.getLength();
            bottomNote.setText(length > max_description_length ?
                    "Your input will be truncated" :
                    max_description_length - length + " characters remaining.");
        });
        setCompanyNameBtn.textProperty().set("Set new name");
        setCompanyNameBtn.setOnAction(e -> {
            String name = companyNameField.getText().substring(0, Math.min(max_name_length, companyNameField.getLength()));
            model.setName(name);
            companyNameTxt.setText(name);
        });
        setCompanyDescriptionBtn.textProperty().set("Set new description");
        setCompanyDescriptionBtn.setOnAction(e -> {
            String description = companyDescriptionField.getText().substring(0, Math.min(max_description_length, companyDescriptionField.getLength()));
            model.setDescription(description);
            companyDescriptionTxt.setText(description);
        });
        HBox topHbox = new HBox(companyNameField, topNote, setCompanyNameBtn);
        topHbox.setSpacing(10);
        HBox bottomHbox = new HBox(setCompanyDescriptionBtn, bottomNote);
        bottomHbox.setSpacing(10);
        VBox dialogVbox = new VBox(topHbox,
                companyDescriptionField, bottomHbox);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);
        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    @FXML
    private void createNewDepartment(ActionEvent event) {
        int max_name_length = 16;
        int max_description_length = 270;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(view.getStage());
        dialog.setTitle("Create New Department");
        dialog.setResizable(false);
        TextField departmentNameField = new TextField();
        TextArea departmentDescriptionField = new TextArea();
        Button setDepartmentDescriptionBtn = new Button();
        Text topNote = new Text(max_name_length + " characters remaining.");
        Text bottomNote = new Text(max_description_length + " characters remaining.");
        departmentNameField.setPromptText("Set department name");
        departmentNameField.setOnKeyTyped(e -> {
            int length = departmentNameField.getLength();
            topNote.setText(length > max_name_length ?
                    "Your input will be truncated" :
                    max_name_length - length + " characters remaining.");
        });
        departmentDescriptionField.setPromptText("Department description");
        departmentDescriptionField.setWrapText(true);
        departmentDescriptionField.setOnKeyTyped(e -> {
            int length = departmentDescriptionField.getLength();
            bottomNote.setText(length > max_description_length ?
                    "Your input will be truncated" :
                    max_description_length - length + " characters remaining.");
        });
        setDepartmentDescriptionBtn.textProperty().set("Create department");
        setDepartmentDescriptionBtn.setOnAction(e -> {
            if (departmentNameField.getLength() == 0) {
                WarningPopup.createWarningPopup("No Name Provided", "Department name can not be empty!", dialog);
            } else {
                String name = departmentNameField.getText().substring(0, Math.min(max_name_length, departmentNameField.getLength()));
                String description = departmentDescriptionField.getText().substring(0, Math.min(max_description_length, departmentDescriptionField.getLength()));
                Department department = new Department(name, description);
                this.model.addDepartment(department);
                ObservableList<Department> departmentNames = departmentsComboBox.getItems();
                boolean containsSearchStr = departmentNames.stream().anyMatch(dep -> name.equalsIgnoreCase(dep.getName()));
                if (containsSearchStr) {
                    WarningPopup.createWarningPopup("Department Already Exists", "A department with this name already exists in this company!", dialog);
                } else {
                    departmentNames.add(department);
                    this.departmentCountTxt.setText("Number of Departments: " + this.model.getDepartments().size());
                    dialog.close();
                }
            }
        });
        HBox topHbox = new HBox(departmentNameField, topNote);
        topHbox.setSpacing(10);
        HBox bottomHbox = new HBox(setDepartmentDescriptionBtn, bottomNote);
        bottomHbox.setSpacing(10);
        VBox dialogVbox = new VBox(topHbox,
                departmentDescriptionField, bottomHbox);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setSpacing(10);
        Scene dialogScene = new Scene(dialogVbox);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @FXML
    private void editDepartmentDetails(ActionEvent event) {
        Department choice = departmentsComboBox.getSelectionModel().getSelectedItem();
        if (!(choice == null)) {
            int choiceIndex = this.model.getDepartments().indexOf(choice);
            int max_name_length = 16;
            int max_description_length = 270;
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(view.getStage());
            dialog.setTitle("Edit Department Details");
            dialog.setResizable(false);
            TextField departmentNameField = new TextField();
            TextArea departmentDescriptionField = new TextArea();
            Button setDepartmentNameBtn = new Button();
            Button setDepartmentDescriptionBtn = new Button();
            Text topNote = new Text(max_name_length + " characters remaining.");
            Text bottomNote = new Text(max_description_length + " characters remaining.");
            departmentNameField.setPromptText("New department name");
            departmentNameField.setOnKeyTyped(e -> {
                int length = departmentNameField.getLength();
                topNote.setText(length > max_name_length ?
                        "Your input will be truncated" :
                        max_name_length - length + " characters remaining.");
            });
            departmentDescriptionField.setPromptText("New department description");
            departmentDescriptionField.setWrapText(true);
            departmentDescriptionField.setOnKeyTyped(e -> {
                int length = departmentDescriptionField.getLength();
                bottomNote.setText(length > max_description_length ?
                        "Your input will be truncated" :
                        max_description_length - length + " characters remaining.");
            });
            setDepartmentNameBtn.textProperty().set("Set new name");
            setDepartmentNameBtn.setOnAction(e -> {
                String name = departmentNameField.getText().substring(0, Math.min(max_name_length, departmentNameField.getLength()));
                choice.setName(name);
                this.model.getDepartments().get(choiceIndex).setName(name);
                departmentsComboBox.getSelectionModel().clearSelection();
                departmentsComboBox.getSelectionModel().select(choice);
                selectedDepartmentName.setText("Selected Department: " + choice.getName());
            });
            setDepartmentDescriptionBtn.textProperty().set("Set new description");
            setDepartmentDescriptionBtn.setOnAction(e -> {
                String description = departmentDescriptionField.getText().substring(0, Math.min(max_description_length, departmentDescriptionField.getLength()));
                this.model.getDepartments().get(choiceIndex).setDescription(description);
                departmentDescriptionTxt.setText(choice.getDescription());
                choice.setDescription(description);
            });
            HBox topHbox = new HBox(departmentNameField, topNote, setDepartmentNameBtn);
            topHbox.setSpacing(10);
            HBox bottomHbox = new HBox(setDepartmentDescriptionBtn, bottomNote);
            bottomHbox.setSpacing(10);
            VBox dialogVbox = new VBox(topHbox,
                    departmentDescriptionField, bottomHbox);
            dialogVbox.setPadding(new Insets(20, 20, 20, 20));
            dialogVbox.setSpacing(10);
            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        }
        else {
            WarningPopup.createWarningPopup("No Department Selected", "Select a department from the dropdown first to edit.", this.view.getStage());
        }
    }
    @FXML
    private void setCompanySalaryBudget(ActionEvent event) {

    }

}
