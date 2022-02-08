package fxtris.Main.Stages;

import fxtris.Main.Controls.Keyboard;
import fxtris.Main.GameEvents.Events;
import fxtris.Main.Others.GlobalValues;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.ESCAPE;

public class SettingsStage {

    private static final Stage settingsStage = new Stage();
    public static Stage getSettingsStage() {
        return settingsStage;
    }

    /**
     * @param i Text object to format
     * @return a formatted and ready to add Text object
     */
    private static Text formatText(Text i){
        i.setFont(Font.font("Verdana", 20));
        return i;
    }

    public static Slider musicVolume = new Slider();
    public static CheckBox connected = new CheckBox("Connected skin");
    private static final TextField sdf = new TextField();
    private static final TextField arr = new TextField();
    private static final TextField das = new TextField();

    /**
     * Loads the settings window and its tabs
     */
    public static void loadSettings(){

        Tab instructionsTab = new Tab("Instructions");
        instructionsTab.setClosable(false);
        instructionsTab.setContent(new VBox(
                new Text(""),
                formatText(new Text("Left: Left Arrow Key")),
                new Text(""),
                formatText(new Text("Right: Right Arrow Key")),
                new Text(""),
                formatText(new Text("Soft Drop: Down Arrow Key")),
                new Text(""),
                formatText(new Text("Hard Drop: S key")),
                new Text(""),
                formatText(new Text("Rotate Clockwise: D key")),
                new Text(""),
                formatText(new Text("Rotate Counterclockwise: A key")),
                new Text(""),
                formatText(new Text("Rotate 180 Degrees: W key")),
                new Text(""),
                formatText(new Text("Hold: F key")),
                new Text(""),
                formatText(new Text("Quick Restart: X key")),
                new Text(""),
                new Text("I don't feel adding adding key rebinding anymore...")
        ));

        das.setFont(Font.font("Verdana", 20));
        arr.setFont(Font.font("Verdana", 20));
        sdf.setFont(Font.font("Verdana", 20));
        Button confirm = new Button("Confirm");
        confirm.setOnMouseClicked(mouseEvent -> {
            try {
                if (Integer.parseInt(String.valueOf(das.getCharacters())) < 0){
                    das.setText("0");
                } else if (Integer.parseInt(String.valueOf(das.getCharacters())) > 20){
                    das.setText("20");
                }
                if (Integer.parseInt(String.valueOf(arr.getCharacters())) < 0){
                    arr.setText("0");
                } else if (Integer.parseInt(String.valueOf(arr.getCharacters())) > 10){
                    arr.setText("10");
                }
                if (Integer.parseInt(String.valueOf(sdf.getCharacters())) < 5){
                    sdf.setText("5");
                } else if (Integer.parseInt(String.valueOf(sdf.getCharacters())) > 61){
                    sdf.setText("61");
                }
            Events.updateSettings(
                Integer.parseInt(String.valueOf(das.getCharacters())),
                Integer.parseInt(String.valueOf(arr.getCharacters())),
                Integer.parseInt(String.valueOf(sdf.getCharacters()))
            );
            } catch (Exception e){
                das.setText(String.valueOf(GlobalValues.getDas()));
                arr.setText(String.valueOf(GlobalValues.getArr()));
                sdf.setText(String.valueOf(GlobalValues.getSdf()));
            }
        });
        Tab gameplay = new Tab("Gameplay");
        gameplay.setClosable(false);
        gameplay.setContent(new VBox(
                formatText(new Text("Delayed Auto Shift (DAS):")),das,
                formatText(new Text("Automatic Repeat Rate (ARR):")),arr,
                formatText(new Text("Soft Drop Factor (SDF):")),sdf,
                confirm
        ));

        musicVolume.setMax(100);
        musicVolume.setMin(0);
        musicVolume.setValue(75);
        Tab others = new Tab("Others");
        others.setClosable(false);
        others.setContent(new VBox(
                formatText(new Text("Music volume (broken):")),
                musicVolume,
                formatText(new Text("Skin:")),
                connected
        ));

        TabPane settingsTabs = new TabPane();
        settingsTabs.getTabs().addAll(instructionsTab,gameplay,others);

        settingsTabs.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(ESCAPE)){
                SettingsStage.getSettingsStage().close();
            }
        });

        VBox settingsRoot = new VBox(settingsTabs);
        Scene settingsScene = new Scene(settingsRoot, 400, 600);
        settingsStage.setScene(settingsScene);
        settingsStage.setResizable(false);
        try {
            settingsStage.initModality(Modality.APPLICATION_MODAL);
        } catch (Exception ignored){}
    }

    /**
     * Updates the values in the tex fields before showing the settings stage
     */
    public static void openSettings() {
        if (!Keyboard.isLeft() && !Keyboard.isRight() && !Keyboard.isSoftDrop()) {
            das.setText(String.valueOf(GlobalValues.getDas()));
            arr.setText(String.valueOf(GlobalValues.getArr()));
            sdf.setText(String.valueOf(GlobalValues.getSdf()));
            settingsStage.show();
        }
    }

    public static boolean isShowing() {
        return settingsStage.isShowing();
    }
}
