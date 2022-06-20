package com.example.trex_but_actually_nah;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/menu.fxml"));
        Game c = loader.getController();
        Scene scene = new Scene(loader.load(), 600, 800);
        primaryStage.setTitle("T-Rex but actually nah!");
        primaryStage.getIcons().add(new Image("file:src/main/resources/com/example/trex_but_actually_nah/img/logo.png"));
        scene.getStylesheets().add(getClass().getResource("layout/Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}