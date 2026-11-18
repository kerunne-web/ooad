package com.banking.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

    private TextField customerIDField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label messageLabel;

    public LoginView() {

        setSpacing(15);
        setPadding(new Insets(20));

        Label heading = new Label("Banking System Login");

        customerIDField = new TextField();
        customerIDField.setPromptText("Enter Customer ID");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        loginButton = new Button("Login");

        messageLabel = new Label();

        getChildren().addAll(
                heading,
                customerIDField,
                passwordField,
                loginButton,
                messageLabel
        );
    }

    public String getCustomerID() {
        return customerIDField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }
}