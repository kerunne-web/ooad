package com.banking;

import com.banking.database.DatabaseInitializer;
import com.banking.database.TestDataInitializer;
import com.banking.view.LoginView;
import com.banking.controller.LoginController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        if (!DatabaseInitializer.initialize()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Database initialization failed");
            alert.setContentText(
                    "The application could not connect to the database.");
            alert.showAndWait();

            return;
        }

        TestDataInitializer.createTestCustomer();

        LoginView loginView = new LoginView();

        new LoginController(loginView, primaryStage);

        Scene scene = new Scene(loginView, 500, 350);

        primaryStage.setTitle("Banking Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}