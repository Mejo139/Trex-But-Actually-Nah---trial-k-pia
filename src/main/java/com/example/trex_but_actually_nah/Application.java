package com.example.trex_but_actually_nah;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Controller c = loader.getController();
        Scene scene = new Scene(loader.load(), 600, 800);
        stage.setTitle("T-Rex but actually nah!");
        stage.getIcons().add(new Image("file:src/main/resources/com/example/trex_but_actually_nah/img/logo.png"));
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}