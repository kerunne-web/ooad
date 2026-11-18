package com.banking.view;

import com.banking.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransactionView extends VBox {

    private TableView<Transaction> transactionTable;
    private Label messageLabel;

    public TransactionView() {

        Label title = new Label("TRANSACTION HISTORY");

        transactionTable = new TableView<>();

        TableColumn<Transaction, String> idColumn =
                new TableColumn<>("Transaction ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, String> typeColumn =
                new TableColumn<>("Type");

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type"));

        TableColumn<Transaction, BigDecimal> amountColumn =
                new TableColumn<>("Amount");

        amountColumn.setCellValueFactory(
                new PropertyValueFactory<>("amount"));

        TableColumn<Transaction, java.time.LocalDateTime> dateColumn =
                new TableColumn<>("Date");

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        transactionTable.getColumns().addAll(
                idColumn,
                typeColumn,
                amountColumn,
                dateColumn
        );

        transactionTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        messageLabel = new Label();

        setSpacing(15);
        setPadding(new Insets(20));

        Button closeButton = new Button("Close");

        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });

        getChildren().addAll(
                title,
                transactionTable,
                messageLabel,
                closeButton
        );
    }

    public void displayTransactions(List<Transaction> transactions) {

        transactionTable.setItems(
                FXCollections.observableArrayList(transactions));
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }
}