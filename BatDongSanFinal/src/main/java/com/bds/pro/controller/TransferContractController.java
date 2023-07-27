/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.Transfer;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class TransferContractController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    GridPane gridTransfer;

    GridTable gt = new GridTable();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createHeader();
        setDataGrid();
    }

    private void createHeader() {
        try {
            gridTransfer.add(gt.cellHeaderNA("ID", 35, 85), 0, 0);
            gridTransfer.add(gt.cellHeaderNA("Transfer Customer", 35, 225), 1, 0);
            gridTransfer.add(gt.cellHeaderNA("Product", 35, 225), 2, 0);
            gridTransfer.add(gt.cellHeaderNA("Transfer Value", 35, 225), 3, 0);
            gridTransfer.add(gt.cellHeaderNA("Action", 35, 175), 4, 0);
        } catch (Exception e) {
            System.out.println("ERROR createHeader");
            System.out.println(e.getMessage());
        }
    }

    private void setDataGrid() {
        try {
            ObservableList<Transfer> ls = DBConnection.getInstance().findTransfer();
            for (int i = 0; i < ls.size(); i++) {
                if (ls.get(i).getStatus() == 0) {
                    String No = String.valueOf(i);
                    gridTransfer.add(gt.cellData(No, 35, 85), 0, i + 1);
                    gridTransfer.add(gt.cellData(ls.get(i).getNameClient(), 35, 225), 1, i + 1);
                    gridTransfer.add(gt.cellData(ls.get(i).getNameProduct(), 35, 225), 2, i + 1);
                    gridTransfer.add(gt.cellData(ls.get(i).getMoney(), 35, 225), 3, i + 1);
                    gridTransfer.add(gt.cellButton(btnAction(ls.get(i).getMaHD()), 35, 175), 4, i + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR setDataGrid");
            System.out.println(e.getMessage());
        }
    }

    public Button btnAction(String MaHD) {
        Button btn = new Button("Create Receipt");
        try {
            btn.setStyle("-fx-text-fill:#038f1f");
            btn.setOnAction((eh) -> {
                ValueCheck.HDCN = MaHD;
                try {
                    createReceipt();
                } catch (IOException e) {
                    System.out.println("ERROR createReceipt");
                    System.out.println(e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("ERROR createReceipt");
            System.out.println(e.getMessage());
        }
        return btn;
    }

    private void createReceipt() throws IOException {
        Stage recepitStage = new Stage();
        recepitStage.initModality(Modality.APPLICATION_MODAL);
        recepitStage.setResizable(false);
        Scene sc = new Scene(App.loadFXML(StringValue.ReceiptTransfer));
        recepitStage.setScene(sc);
        recepitStage.show();
    }

    public void saleContract() throws IOException {
        App.setRoot(StringValue.SaleContract);
    }

    public void depositContract() throws IOException {
        App.setRoot(StringValue.DepositContract);
    }

    public void Announce() throws IOException {
        App.setRoot(StringValue.Announce);
    }

    public void Recover() throws IOException {
        App.setRoot(StringValue.Recover);
    }

   public void btnLogout() throws IOException {
        Stage thisStage = (Stage) gridTransfer.getScene().getWindow();
        thisStage.close();
        thisStage.setTitle("Dialog");
        Group root = new Group();
        App.scene = new Scene(root, 1366, 768, Color.valueOf("#e4dfd7"));

        thisStage.setScene(App.scene);
        thisStage.show();

        Stage myDialog = new MyDialog(thisStage);
        myDialog.sizeToScene();
        myDialog.show();
    }
}
