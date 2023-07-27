/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author thuan
 */
public class AlertHandling {
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
     public static boolean confirmAlert (String title, String message){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle(title);
         alert.setHeaderText(null);
         alert.setContentText(message);
         Optional<ButtonType> btn = alert.showAndWait();
         return btn.get() == ButtonType.OK;
     }
}
