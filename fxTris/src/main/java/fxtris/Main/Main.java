package fxtris.Main;

import fxtris.Main.Minoes.Tetromino;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class Main extends Application {

    static Group root = new Group();
    static Group settings = new Group();

    static Tetromino currentTetromino = new Tetromino();

    @Override
    public void start(Stage stage) throws IOException {



        AnimationTimer frames = new AnimationTimer() {

            @Override
            public void handle(long l) {

                //Write game frames here

            }
        };

        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        stage.setTitle("fxTris");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        frames.start();
    }

    public static void main(String[] args) {
        launch();
    }
}