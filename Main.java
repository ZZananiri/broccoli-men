import javafx.application.Application;
import javafx.stage.Stage;
import models.CompanyModel;
import views.MainView;

public class Main extends Application {
    CompanyModel companyModel;
    MainView view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.companyModel = new CompanyModel(); // create a model
        this.view = new MainView(companyModel, primaryStage); //tie the model to the view
    }
}