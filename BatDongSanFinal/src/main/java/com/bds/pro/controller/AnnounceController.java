/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.Announce;
import com.bds.pro.model.Batch;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class AnnounceController implements Initializable {

    /**
     * Initializes the controller class.
     */
    GridTable gt = new GridTable();

    ObservableList<Announce> listShow = FXCollections.observableArrayList();
    ObservableList<Batch> listJson = FXCollections.observableArrayList();

    @FXML
    GridPane gridAnnounce;

    Integer Batch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createHeader();
        startCheck();
        showDataGrid();
    }

    private void createHeader() {
        try {
            gridAnnounce.add(gt.cellHeaderNA("ID", 35, 85), 0, 0);
            gridAnnounce.add(gt.cellHeaderNA("Name Client", 35, 225), 1, 0);
            gridAnnounce.add(gt.cellHeaderNA("Address", 35, 225), 2, 0);
            gridAnnounce.add(gt.cellHeaderNA("Phone", 35, 225), 3, 0);
            gridAnnounce.add(gt.cellHeaderNA("Product", 35, 175), 4, 0);
            gridAnnounce.add(gt.cellHeaderNA("Action", 35, 175), 5, 0);
        } catch (Exception e) {
            System.out.println("ERROR createHeader");
            System.out.println(e.getMessage());
        }
    }

    private void startCheck() {
        ObservableList<Announce> ls = DBConnection.getInstance().GET_DEBT();
        try {

            for (int i = 0; i < ls.size(); i++) {
                if (checkDateReminer(ls.get(i).getDotThanhToan()) == false && checkReminer(ls.get(i).getMaHD()) == false) {
                    Announce an = new Announce();
                    an.setAddress(ls.get(i).getAddress());
                    an.setBatch(String.valueOf(Batch));
                    an.setMaHD(ls.get(i).getMaHD());
                    an.setNameClient(ls.get(i).getNameClient());
                    an.setPhone(ls.get(i).getPhone());
                    an.setProduct(ls.get(i).getProduct());
                    listShow.add(an);
                }
                listJson.clear();
            }
        } catch (Exception e) {
            System.out.println("ERROR startCheck");
            System.out.println(e.getMessage());
        }
    }

    private boolean checkReminer(String MaHD) {
        Boolean Check = false;
        try {
            Integer Count = DBConnection.getInstance().checkReminer(MaHD, String.valueOf(Batch));
            if (Count != 0) {
                Check = true;
            }
        } catch (Exception e) {
            System.out.println("ERROR checkReminer");
            System.out.println(e.getMessage());
        }
        return Check;
    }

    private boolean checkDateReminer(String jsonString) {
        Boolean Check = false;
        try {
            String jsonDate = getDatefromJson(jsonString);
            Calendar cal = Calendar.getInstance();
            Integer currentMonth = cal.get(Calendar.MONTH) + 1;
            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(jsonDate);
            Calendar calSet = Calendar.getInstance();
            calSet.setTime(d);
            Integer secondMonth = calSet.get(Calendar.MONTH) + 1;
            if (secondMonth - currentMonth != 1) {
                Check = true;
            }
        } catch (ParseException e) {
            System.out.println("ERROR checkDateReminer");
            System.out.println(e.getMessage());
        }
        return Check;
    }

    private String getDatefromJson(String jsonString) {
        String Date = "";
        try {
            makeListbyJson(jsonString);
            for (int i = 0; i < listJson.size(); i++) {
                if ("0".equals(listJson.get(i).getStatus())) {
                    Date = listJson.get(i).getDate();
                    Batch = Integer.parseInt(listJson.get(i).getID());
                    break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR getDatefromJson");
            System.out.println(e.getMessage());
        }
        return Date;
    }

    private void makeListbyJson(String jsonString) {
        try {
            JSONArray jsonarray = new JSONArray(jsonString);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Batch bt = new Batch();
                bt.setID(jsonobject.getString("ID"));
                bt.setMoney(jsonobject.getString("money"));
                bt.setDate(jsonobject.getString("date"));
                bt.setStatus(jsonobject.getString("status"));
                listJson.add(bt);
            }
        } catch (JSONException e) {
            System.out.println("ERROR makeListbyJson");
            System.out.println(e.getMessage());
        }
    }

    private void showDataGrid() {
        try {
            for (int i = 0; i < listShow.size(); i++) {
                String No = String.valueOf(i + 1);
                gridAnnounce.add(gt.cellData(No, 35, 85), 0, i + 1);
                gridAnnounce.add(gt.cellData(listShow.get(i).getNameClient(), 35, 225), 1, i + 1);
                gridAnnounce.add(gt.cellData(listShow.get(i).getAddress(), 35, 225), 2, i + 1);
                gridAnnounce.add(gt.cellData(String.valueOf(listShow.get(i).getPhone()), 35, 225), 3, i + 1);
                gridAnnounce.add(gt.cellData(listShow.get(i).getProduct(), 35, 175), 4, i + 1);
                String Split = listShow.get(i).getMaHD() + "-" + listShow.get(i).getBatch();
                gridAnnounce.add(gt.cellButton(btnReminder(Split), 35, 175), 5, i + 1);
            }
        } catch (Exception e) {
            System.out.println("ERROR showDataGrid");
            System.out.println(e.getMessage());
        }
    }

    private Button btnReminder(String MaHD) {
        Button btn = new Button("Reminder");
        try {
            btn.setId(MaHD);
            btn.setOnAction(eh -> {
                ValueCheck.FristDebt = btn.getId();
                try {
                    createReminder();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("ERROR btnReminder");
            System.out.println(e.getMessage());
        }
        return btn;
    }

    private void createReminder() throws IOException {
        Stage recepitStage = new Stage();
        recepitStage.initModality(Modality.APPLICATION_MODAL);
        recepitStage.setResizable(false);
        Scene sc = new Scene(App.loadFXML(StringValue.FristDebt));
        recepitStage.setScene(sc);
        recepitStage.show();
    }

    public void saleContract() throws IOException {
        App.setRoot(StringValue.SaleContract);
    }

    public void transferContract() throws IOException {
        App.setRoot(StringValue.TransferContract);
    }

    public void depositContract() throws IOException {
        App.setRoot(StringValue.DepositContract);
    }

    public void Recover() throws IOException {
        App.setRoot(StringValue.Recover);
    }

    public void btnLogout() throws IOException {
        Stage thisStage = (Stage) gridAnnounce.getScene().getWindow();
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
