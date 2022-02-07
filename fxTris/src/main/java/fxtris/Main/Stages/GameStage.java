package fxtris.Main.Stages;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static fxtris.Main.Others.GlobalValues.*;

public class GameStage {

    public static Group root = new Group();
    public static Scene scene = new Scene(root, 600, 800, Color.BLACK);

    public static Text clears = new Text("Tetris");
    public static Text tspin = new Text("T-Spin");
    public static Text perfectClear = new Text("Perfect\n  Clear");

    public static Line left = new Line(TILE * LEFTWALL + TILE, TILE * 5, TILE * LEFTWALL + TILE, GROUND * TILE);
    public static Line right = new Line(TILE * RIGHTWALL, TILE * 5, TILE * RIGHTWALL, TILE * GROUND);
    public static Line down = new Line(TILE * RIGHTWALL + (TILE * 6), TILE * GROUND, TILE, GROUND * TILE);
    public static Line top = new Line(TILE * RIGHTWALL + (TILE * 6), TILE * 5, TILE, TILE * 5);

    public static Button toggleMusic = new Button();
    public static Button toggleSettings = new Button();
    public static boolean musicOn = true;

    /**
     *
     * @param line line to format and add to the root
     */
    private static void addLine(Line line) {
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);
        root.getChildren().add(line);
    }

    public static Scene getTheScene(){

        tspin.setFill(Color.WHITE);
        tspin.setX(35);
        tspin.setY(300);
        tspin.setFont(Font.font(40));
        tspin.setOpacity(0);

        clears.setFill(Color.WHITE);
        clears.setX(35);
        clears.setY(350);
        clears.setFont(Font.font(40));
        clears.setOpacity(0);

        perfectClear.setFill(Color.WHITE);
        perfectClear.setX(190);
        perfectClear.setY(400);
        perfectClear.setFont(Font.font(70));
        perfectClear.setOpacity(0);
        perfectClear.setFill(Color.YELLOW);

        Text holdtxt = new Text("HOLD");
        holdtxt.setFill(Color.WHITE);
        holdtxt.setX(50);
        holdtxt.setY(100);
        holdtxt.setFont(Font.font(40));

        Text nexttxt = new Text("NEXT");
        nexttxt.setFill(Color.WHITE);
        nexttxt.setX(470);
        nexttxt.setY(100);
        nexttxt.setFont(Font.font(40));

        root.getChildren().add(holdtxt);
        root.getChildren().add(nexttxt);
        root.getChildren().add(clears);
        root.getChildren().add(tspin);
        root.getChildren().add(perfectClear);

        addLine(left);
        addLine(right);
        addLine(down);
        addLine(top);

        toggleMusic.setStyle("-fx-background-color: #000000;");
        toggleMusic.setTranslateX(490);
        toggleMusic.setTranslateY(730);
        toggleMusic.setPrefSize(30, 30);
        toggleMusic.setFocusTraversable(false);
        GameStage.root.getChildren().add(toggleMusic);
        toggleSettings.setStyle("-fx-background-color: #000000;");
        toggleSettings.setTranslateX(490);
        toggleSettings.setTranslateY(680);
        toggleSettings.setPrefSize(30, 30);
        toggleSettings.setFocusTraversable(false);
        GameStage.root.getChildren().add(toggleSettings);

        return scene;
    }
}
