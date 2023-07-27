/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.Deposit;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.MyDialog;
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
public class DepositContractController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    GridPane gridDeposit;

    GridTable gt = new GridTable();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createHeader();
        setDataGrid();
    }

    private void createHeader() {
        try {
            gridDeposit.add(gt.cellHeaderNA("ID", 35, 75), 0, 0);
            gridDeposit.add(gt.cellHeaderNA("Client", 35, 225), 1, 0);
            gridDeposit.add(gt.cellHeaderNA("Product", 35, 225), 2, 0);
            gridDeposit.add(gt.cellHeaderNA("Deposits", 35, 225), 3, 0);
            gridDeposit.add(gt.cellHeaderNA("Action", 35, 185), 4, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setDataGrid() {
        try {
            ObservableList<Deposit> ls = DBConnection.getInstance().findDeposit();
            for (int i = 0; i < ls.size(); i++) {
                if (ls.get(i).getStatus() == 0) {
                    String No = String.valueOf(i + 1);
                    gridDeposit.add(gt.cellData(No, 35, 75), 0, i + 1);
                    gridDeposit.add(gt.cellData(ls.get(i).getNameClient(), 35, 225), 1, i + 1);
                    gridDeposit.add(gt.cellData(ls.get(i).getNameProduct(), 35, 225), 2, i + 1);
                    gridDeposit.add(gt.cellData(ls.get(i).getDeposits(), 35, 225), 3, i + 1);
                    gridDeposit.add(gt.cellButton(btnAction(ls.get(i).getMaHD()), 35, 185), 4, i + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR setDataGrid");
            System.out.println(e.getMessage());
        }
    }

    private Button btnAction(String MaHD) {
        Button btn = new Button("Create Receipt");
        try {
            btn.setStyle("-fx-text-fill:#038f1f");          
            btn.setOnAction((eh) -> {
                ValueCheck.HDDC=MaHD;
                try {
                    createReceipt();
                } catch (IOException e) {
                    System.out.println("ERROR stageDeposit");
                    System.out.println(e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("btnAction");
            System.out.println(e.getMessage());
        }
        return btn;
    }

    private void createReceipt() throws IOException {
        Stage recepitStage = new Stage();
        recepitStage.initModality(Modality.APPLICATION_MODAL);
        recepitStage.setResizable(false);
        Scene sc = new Scene(App.loadFXML(StringValue.ReceiptDeposit));
        recepitStage.setScene(sc);
        recepitStage.show();
    }

    public void saleContract() throws IOException {
        App.setRoot(StringValue.SaleContract);
    }

    public void transferContract() throws IOException {
        App.setRoot(StringValue.TransferContract);
    }
    
    public void Announce() throws IOException{
        App.setRoot(StringValue.Announce);
    }
    
    public void Recover() throws IOException{
        App.setRoot(StringValue.Recover);
    }
    
   public void btnLogout() throws IOException {
        Stage thisStage = (Stage) gridDeposit.getScene().getWindow();
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
