/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class KeToanPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void saleContract() throws IOException{
       App.setRoot(StringValue.SaleContract);
    }
    
    public void transferContract() throws IOException{
       App.setRoot(StringValue.TransferContract);
    }
    
    public void depositContract() throws IOException{
        App.setRoot(StringValue.DepositContract);
    }
    
    public void Announce() throws IOException{
        App.setRoot(StringValue.Announce);
    }
    
    public void Recover() throws IOException{
        App.setRoot(StringValue.Recover);
    }
}
