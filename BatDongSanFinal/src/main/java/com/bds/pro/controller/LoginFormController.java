/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.Login;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class LoginFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    MFXTextField tfUserName;

    @FXML
    MFXPasswordField tfPassword;

    @FXML
    Label lbUserName, lbPassword;

    private boolean Error;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
    }

    public void btnLogin() {
        try {
            String UserName = checkUserName();
            String Password = checkPassword();
            checkLabelError();
            if (Error == true) {
                showAlert(Alert.AlertType.ERROR, "Error Login", "Please Check Back");
            } else {
                ObservableList<Login> ls = DBConnection.getInstance().checkLogin(UserName, Password);
                if (ls.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error Login", "Please Check Back");
                } else if (ls.get(0).getStatus() == 0) {
                    showAlert(Alert.AlertType.ERROR, "Error Login", "Please Check Back");
                } else {
                    switch (ls.get(0).getDepartment()) {
                        case 1:
                            //Kinh Doanh
                            ValueCheck.IDNV = ls.get(0).getID();
                            Stage thisStageKD = (Stage) tfPassword.getScene().getWindow();
                            thisStageKD.setResizable(Error);
                            thisStageKD.close();
                            App.setRoot(StringValue.MainPage);
                            break;
                        case 2:
                            //Ke Toan                         
                            Stage thisStage = (Stage) tfPassword.getScene().getWindow();
                            thisStage.close();
                            thisStage.setResizable(true);

                            App.setRoot(StringValue.KeToanPage);
                            ValueCheck.IDNV = ls.get(0).getID();
                            break;
                        case 3:                        
                            Stage thisStageGD = (Stage) tfPassword.getScene().getWindow();
                            thisStageGD.close();
                            thisStageGD.setResizable(true);

                            App.setRoot(StringValue.CEO_MAIN);
                            ValueCheck.IDNV = ls.get(0).getID();
                        default:
                            throw new AssertionError();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR btnLogin");
            System.out.println(e.getMessage());
        }
    }

    public void btnReset() {
        tfPassword.clear();
        tfUserName.clear();
        tfUserName.requestFocus();
    }

    private String checkUserName() {
        String username = "";
        try {
            if (tfUserName.getText().length() == 0) {
                lbUserName.setText("Please Input This Field");
            } else {
                lbUserName.setText("");
                username = tfUserName.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkUserName");
            System.out.println(e.getMessage());
        }
        return username;
    }

    private String checkPassword() {
        String password = "";
        try {
            if (tfPassword.getText().length() == 0) {
                lbPassword.setText("Please Input This Field");
            } else {
                lbPassword.setText("");
                password = tfPassword.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkPassword");
            System.out.println(e.getMessage());
        }
        return password;
    }

    private void checkLabelError() {
        List<Label> ls = new ArrayList<>();
        int countError = 0;
        ls.add(lbUserName);
        ls.add(lbPassword);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getText().isBlank()) {
                countError += 1;
            }
        }
        Error = countError != 2;
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
