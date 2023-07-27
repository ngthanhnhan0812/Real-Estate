/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.Batch;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.Sale;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class SaleContractController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    GridPane gridSale;

    GridTable gt = new GridTable();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createHeader();
        setDataGrid();
    }

    private void createHeader() {
        gridSale.add(gt.cellHeaderNA("ID", 35, 75), 0, 0);
        gridSale.add(gt.cellHeaderNA("Client", 35, 225), 1, 0);
        gridSale.add(gt.cellHeaderNA("Product", 35, 225), 2, 0);
        gridSale.add(gt.cellHeaderNA("Installments Payments", 35, 400), 3, 0);
        gridSale.add(gt.cellHeaderNA("Action", 35, 175), 4, 0);
    }

    private void setDataGrid() {
        try {
            ObservableList<Sale> ls = DBConnection.getInstance().findSale();
            for (int i = 0; i < ls.size(); i++) {
                if (checkIsset(ls.get(i).getMaHD()) == false) {
                    String No = String.valueOf(i + 1);
                    gridSale.add(gt.cellData(No, 35, 75), 0, i + 1);
                    gridSale.add(gt.cellData(ls.get(i).getNameClient(), 35, 225), 1, i + 1);
                    gridSale.add(gt.cellData(ls.get(i).getNameProduct(), 35, 225), 2, i + 1);
                    gridSale.add(gt.cellGridPane(jsonGrid(makeListbyJson(ls.get(i).getDotThanhToan())), 35, 400), 3, i + 1);
                    gridSale.add(gt.cellButton(btnAction(ls.get(i).getMaHD()), 35, 175), 4, i + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR setDataGrid");
            System.out.println(e.getMessage());
        }
    }

    private Boolean checkIsset(String MaHD) {
        Boolean Check = false;
        try {
            Integer Count = DBConnection.getInstance().checkReceipt(MaHD);
            if (Count == 4) {
                Check = true;
            }
        } catch (Exception e) {
            System.out.println("ERROR checkIsset");
            System.out.println(e.getMessage());
        }
        return Check;
    }

    private ObservableList<Batch> makeListbyJson(String jsonString) {
        ObservableList<Batch> ls = FXCollections.observableArrayList();
        try {
            JSONArray jsonarray = new JSONArray(jsonString);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Batch bt = new Batch();
                bt.setID(jsonobject.getString("ID"));
                bt.setMoney(jsonobject.getString("money"));
                bt.setDate(jsonobject.getString("date"));
                bt.setStatus(jsonobject.getString("status"));
                ls.add(bt);
            }
        } catch (JSONException e) {
            System.out.println("ERROR makeListbyJson");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    private GridPane jsonGrid(ObservableList<Batch> listBatch) {
        GridPane gp = new GridPane();
        for (int i = 0; i < 4; i++) {
            Label lb = new Label();
            if ("0".equals(listBatch.get(i).getStatus())) {
                lb.setStyle("-fx-text-fill:red");
            } else {
                lb.setStyle("-fx-text-fill:#20e627");
            }
            String Text = "Phase " + listBatch.get(i).getID() + " (" + listBatch.get(i).getDate() + ") "
                    + "Revenue: " + listBatch.get(i).getMoney() + "VNĐ";
            lb.setMinHeight(25);
            gp.setStyle("-fx-alignment: center;");
            lb.setText(Text);
            gp.add(lb, 0, i + 1);
        }
        return gp;
    }

    private Button btnAction(String MaHD) {
        Button btn = new Button("Create Receipt");
        try {
            btn.setStyle("-fx-text-fill:#038f1f");
            btn.setOnAction((eh) -> {
                ValueCheck.HDMB = MaHD;
                try {
                    createReceipt();
                } catch (IOException e) {
                    System.out.println("ERROR StageSale");
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
        Scene sc = new Scene(App.loadFXML(StringValue.ReceiptSale));
        recepitStage.setScene(sc);
        recepitStage.show();
    }

    public void transferContract() throws IOException {
        App.setRoot(StringValue.TransferContract);
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
        Stage thisStage = (Stage) gridSale.getScene().getWindow();
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
