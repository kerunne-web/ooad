package com.banking.view;

import java.math.BigDecimal;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AccountView extends VBox {

    private Label customerNameLabel;
    private Label accountNumberLabel;
    private Label accountTypeLabel;
    private Label balanceLabel;

    private ComboBox<String> accountSelector;

    private TextField amountField;
    private Button depositButton;
    private Button withdrawButton;          // <-- This was missing
    private Button historyButton;
    private Label messageLabel;

    public AccountView() {

        Label title = new Label("ACCOUNT DASHBOARD");

        customerNameLabel = new Label("Customer: ");
        accountNumberLabel = new Label("Account Number: ");
        accountTypeLabel = new Label("Account Type: ");
        balanceLabel = new Label("Balance: BWP 0.00");

        Label accountLabel = new Label("Select Account:");

        accountSelector = new ComboBox<>();
        accountSelector.setPromptText("Choose an account");
        accountSelector.setMaxWidth(Double.MAX_VALUE);

        Label amountLabel = new Label("Enter Amount:");

        amountField = new TextField();
        amountField.setPromptText("Enter amount in BWP");

        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");   // <-- This was missing
        historyButton = new Button("Transaction History");

        messageLabel = new Label();

        setSpacing(15);
        setPadding(new Insets(25));
        setPrefWidth(400);

        getChildren().addAll(
                title,
                customerNameLabel,
                accountLabel,
                accountSelector,
                accountNumberLabel,
                accountTypeLabel,
                balanceLabel,
                amountLabel,
                amountField,
                depositButton,
                withdrawButton,
                historyButton,
                messageLabel
        );
    }

    public void setAccountOptions(List<String> accounts) {
        accountSelector.getItems().setAll(accounts);
    }

    public ComboBox<String> getAccountSelector() {
        return accountSelector;
    }

    public void setSelectedAccount(int index) {
        accountSelector.getSelectionModel().select(index);
    }

    public void setAccountInformation(
            String customerName,
            String accountNumber,
            String accountType,
            BigDecimal balance) {

        customerNameLabel.setText("Customer: " + customerName);
        accountNumberLabel.setText("Account Number: " + accountNumber);
        accountTypeLabel.setText("Account Type: " + accountType);
        balanceLabel.setText("Balance: BWP " + balance);
    }

    public String getAmount() {
        return amountField.getText().trim();
    }

    public Button getDepositButton() {
        return depositButton;
    }

    public Button getWithdrawButton() {
        return withdrawButton;
    }

    public Button getHistoryButton() {
        return historyButton;
    }

    public void updateBalance(BigDecimal balance) {
        balanceLabel.setText("Balance: BWP " + balance);
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public void clearAmountField() {
        amountField.clear();
    }
}