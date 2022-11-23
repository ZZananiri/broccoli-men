package views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A class used to create warning popup windows to display messages to the user.
 */
public class WarningPopup {
    /**
     * Creates and displays a warning popup with the specified title, message, and stage owner.
     * @param title the title of the popup window
     * @param message the message shown in the popup window
     * @param ownerStage the stage that owns the popup window
     */
    public static void createWarningPopup(String title, String message, Stage ownerStage) {
        // Creating a stage on which the user will be able to view the warning message
        final Stage warningDialog = new Stage();
        warningDialog.initModality(Modality.APPLICATION_MODAL);
        warningDialog.initOwner(ownerStage);
        warningDialog.setTitle(title);
        warningDialog.setResizable(false);

        // Creating layout for the scene, setting the scene to the stage, and showing the stage
        HBox warningBox = new HBox( new Text(message));
        warningBox.setPadding(new Insets(20));
        warningDialog.setScene(new Scene(warningBox));
        warningDialog.show();
    }
}
