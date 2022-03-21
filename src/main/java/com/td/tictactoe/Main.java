package com.td.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/main-view.fxml"));
        Parent root = loader.load();
        stage.setTitle("Tic Tac Toe");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(725);
        stage.setMinWidth(900);
        stage.setResizable(false);
        stage.getIcons().add(new Image(String.valueOf(Main.class.getResource("img/icon.png"))));
        stage.show();

        Controller controller = loader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
    }

    public static void main(String[] args) {
        launch();
    }
}