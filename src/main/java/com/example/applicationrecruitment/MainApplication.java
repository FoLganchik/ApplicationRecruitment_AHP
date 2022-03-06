package com.example.applicationrecruitment;

import com.example.applicationrecruitment.controller.RootLayoutController;
import com.example.applicationrecruitment.models.AHP;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApplication extends Application {
    public static Stage primaryStage;
    public static Scene rootScene;
    public static BorderPane rootLayout;

    public static RootLayoutController rootLayoutController;

    public static File currentFile;
    public static AHP AHP;

    @Override
    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml-files/root_layout.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
//        stage.setTitle("AHP");
//        stage.setScene(scene);
//        stage.show();

        MainApplication.primaryStage = primaryStage;
        MainApplication.primaryStage.setTitle("AHP");

        MainApplication.primaryStage.getIcons().add( new Image(
                String.valueOf(getClass().getResource("/images/ahp_icon.jpg"))
        ));
        AHP = new AHP();
        RootLayoutController.init();
    }

//    public static void main(String[] args) {
//        launch();
//    }
}