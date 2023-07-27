/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.NhanVien;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ntnha
 */
public class CEOFormController implements Initializable {

    @FXML
    private MenuButton btnChangeKD;

    @FXML
    private JFXButton btnChangeRevenue;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnExit;

    @FXML
    private LineChart<?, ?> cData;

    @FXML
    private AnchorPane firstPane;
    @FXML
    private Label showFullname,showPosition;

    @FXML
    private static final String dbURL = "jdbc:sqlserver://115.73.212.222:8888;databaseName=sem2_ngan;encrypt=true;trustServerCertificate=true;";
    private static final String username = "ngan";
    private static final String password = "123";
    Connection conn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NhanVien nv = DBConnection.getInstance().GetInfEmp(2);
        showFullname.setText(nv.getFullname());
        showPosition.setText(nv.getPosition());
        btnChangeRevenue.addEventHandler(ActionEvent.ACTION, eh -> {
            try {
                AnchorPane classRoom = (AnchorPane) App.loadFXML(StringValue.CEO_REVENUE);
                firstPane.getChildren().clear();
                firstPane.getChildren().add(classRoom);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnChangeKD.addEventHandler(ActionEvent.ACTION, eh -> {
            try {
                AnchorPane classRoom = (AnchorPane) App.loadFXML(StringValue.CEO_MANAGEMENT);
                firstPane.getChildren().clear();
                firstPane.getChildren().add(classRoom);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnExit.addEventHandler(ActionEvent.ACTION, eh -> {
            System.exit(0);
        });

    }

    public void ChangeCEOLoC() throws IOException {
        Parent p = App.loadFXML(StringValue.CEOLoC);
        firstPane.getChildren().clear();
        firstPane.getChildren().add(p);
    }

    public void ChangeCEOLoI() throws IOException {
        Parent p =  App.loadFXML(StringValue.CEOLoI);
        firstPane.getChildren().clear();
        firstPane.getChildren().add(p);
    }

    public void btnLogout() throws IOException {
        Stage thisStage = (Stage) btnChangeKD.getScene().getWindow();
        thisStage.close();
        thisStage.setTitle("CEO");
        Group root = new Group();
        App.scene = new Scene(root, 1366, 768, Color.valueOf("#e4dfd7"));

        thisStage.setScene(App.scene);
        thisStage.show();

        Stage myDialog = new MyDialog(thisStage);
        myDialog.sizeToScene();
        myDialog.show();
    }
}
