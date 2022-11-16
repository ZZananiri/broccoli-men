package controller;

import models.CompanyModel;
import views.MainView;

public class MainController {
    CompanyModel model;
    MainView view;

    public MainController(CompanyModel model, MainView view) {
        this.model = model;
        this.view = view;
    }
}
