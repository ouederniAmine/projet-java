package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LinkedinApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/client/login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
//            scene.setFill(Color.TRANSPARENT);

//            stage.initStyle(StageStyle.TRANSPARENT);
            // full screen mode
            stage.setMaximized(true);
//            stage.setFullScreen(true);
            stage.setScene(scene);
            stage.setTitle("Linkedin Application");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
