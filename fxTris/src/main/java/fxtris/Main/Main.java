package fxtris.Main;

import fxtris.Main.Controls.Controller;
import fxtris.Main.Minoes.Tetromino;
import fxtris.Main.Minoes.Tetrominoes.S;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static fxtris.Main.Others.GlobalValues.*;

public class Main extends Application {

    public static Group root = new Group();
    static Group settings = new Group();

    public static Scene scene = new Scene(root, 800, 800, Color.BLACK);

    public static Tetromino currentTetromino = new Tetromino();

    @Override
    public void start(Stage stage) throws IOException {

        currentTetromino = new S();
        root.getChildren().add(currentTetromino.getMinoA());
        root.getChildren().add(currentTetromino.getMinoB());
        root.getChildren().add(currentTetromino.getMinoC());
        root.getChildren().add(currentTetromino.getMinoCentral());

        AnimationTimer frames = new AnimationTimer() {

            int tempGRV = 0;

            @Override
            public void handle(long l) {

                Controller.loadController();

                if (currentTetromino.isActive()) {

                    tempGRV++;
                    if (tempGRV > GRAVITY / getSdf()) {
                        tempGRV = 0;
                        currentTetromino.getMinoCentral().setY(currentTetromino.getMinoCentral().getY() + TILE);
                    }

                    if (currentTetromino.isColliding()){
                        currentTetromino.setOneSlide(currentTetromino.getOneSlide() -1);
                        currentTetromino.setFourSlide(currentTetromino.getFourSlide() -1);
                        tempGRV = 0;
                    } else if (currentTetromino.isCollided()){
                        currentTetromino.setOneSlide(60);
                    }

                    if (currentTetromino.getOneSlide() < 0 || currentTetromino.getFourSlide() < 0) {
                        currentTetromino.setActive(false);
                    }

                    currentTetromino.getMinoCentral().setFill(Color.RED);
                    currentTetromino.update();
                } else {
                    currentTetromino.getMinoCentral().setFill(Color.GRAY);
                }

            }
        };


        /*Rectangle dsa = new Rectangle(50,50,Color.WHITE);
        ArrayList<Rectangle> qwe = new ArrayList<>();
        qwe.add(dsa);
        root.getChildren().add(qwe.get(0));
        Button asd = new Button();
        asd.setOnAction(actionEvent -> {
            qwe.get(0).setY(100);
        });
        root.getChildren().add(asd);*/

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