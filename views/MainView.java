package views;

import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import models.CompanyModel;

import java.io.IOException;

/**
 * The main view of the application
 */
public class MainView {
    private CompanyModel model; // The model of the company
    private MainController controller;  // The controller responsible for updating the model and view
    private Stage stage;    // The stage on which the application is being displayed.

    /**
     * Constructs a MainView object with the specified company model and application stage.
     * @param model company model to be referenced by the view
     * @param stage the stage on which the view will be displayed
     */
    public MainView(CompanyModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.controller = new MainController(model, this);  // Creating a controller
        initializeView();
    }

    /**
     * Loads the FXML and initializes the view.
     */
    private void initializeView() {
        Parent root;
        try {
            // Loading the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            loader.setController(controller);
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("fxml file not found.");
        }
        // Configuring and showing the stage
        stage.setTitle("Company Management System");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Return the main view's stage.
     * @return the main view's stage.
     */
    public Stage getStage() {
        return this.stage;
    }
}
