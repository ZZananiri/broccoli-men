package views;

import javafx.stage.Stage;

import models.CompanyModel;

public class MainView {
    private CompanyModel model;
    private Stage stage;

    public MainView(CompanyModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initializeView();
    }

    private void initializeView() {

    }
}
