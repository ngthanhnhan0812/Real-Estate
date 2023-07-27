/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author thuan
 */
public class TextFieldHandling {
    AlertHandling alert = new AlertHandling();
    public boolean checkValue (TextField tf) {
        try {
            if(tf.getText().isEmpty()){
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please fill up requirement");
                return false;
            }
        } catch (Exception e) {
            System.out.println("String no ");
        }
        return true;
    }
    
    public boolean validateNumber (TextField tf) {
        try {
                Integer.parseInt(tf.getText());
        } catch (Exception e) {
            alert.showAlert(Alert.AlertType.ERROR, "ERROR", "The value is not valid");
            return false;
        }
        return true;
    }
}
