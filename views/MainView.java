package views;

import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import models.CompanyModel;

import java.io.IOException;

public class MainView {
    private CompanyModel model;
    private MainController controller;
    private Stage stage;

    public MainView(CompanyModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.controller = new MainController(model, this);  // Creating a controller
        initializeView();
    }

    private void initializeView() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
            loader.setController(controller);
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("fxml file not found.");
        }
        stage.setTitle("Company Management System");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public Stage getStage() {
        return this.stage;
    }
}
