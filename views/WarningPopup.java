package views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WarningPopup {
    public static void createWarningPopup(String title, String message, Stage ownerStage) {
        final Stage warningDialog = new Stage();
        warningDialog.initModality(Modality.APPLICATION_MODAL);
        warningDialog.initOwner(ownerStage);
        warningDialog.setTitle(title);
        warningDialog.setResizable(false);
        HBox warningBox = new HBox( new Text(message));
        warningBox.setPadding(new Insets(20));
        warningDialog.setScene(new Scene(warningBox));
        warningDialog.show();
    }
}
