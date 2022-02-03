package fxtris.Main.Stages;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Settings {

    private Group settingsRoot = new Group();
    private Scene settingsScene = new Scene(settingsRoot, 400, 600);
    private Stage settingsStage = new Stage();
    public Stage getSettingsStage() {
        return settingsStage;
    }
    public Scene getSettingsScene() {
        return settingsScene;
    }

    public void openSettings() {
        settingsStage.setScene(settingsScene);
        try {
            settingsStage.initModality(Modality.APPLICATION_MODAL);
        } catch (Exception e){}
        settingsStage.show();
    }

}
