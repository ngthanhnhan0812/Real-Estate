/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.model.ListofCus;
import com.bds.pro.model.ListofInv;
import com.bds.pro.model.createGrid;
import com.bds.pro.model.jpa_controller.CEOLoC_JPA;
import com.bds.pro.model.jpa_controller.CEOLoI_JPA;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author thuan
 */
public class CEOLoIController implements Initializable {

    @FXML
    GridPane gridL;

    CEOLoC_JPA jp = new CEOLoC_JPA();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showHeaderRoww();
        setDataGridd();
    }

    private void showHeaderRoww() {
        try {
            gridL.add(createGrid.createColName("No", 45, 32, "No"), 0, 0);
            gridL.add(createGrid.createColName("ID", 45, 32, "No"), 1, 0);
            gridL.add(createGrid.createColName("Full Name", 195, 32, "No"), 2, 0);
            gridL.add(createGrid.createColName("Phone", 150, 32, "No"), 3, 0);
            gridL.add(createGrid.createColName("Email", 180, 32, "No"), 4, 0);
            gridL.add(createGrid.createColName("Name of Project", 180, 32, "No"), 5, 0);
            gridL.add(createGrid.createColName("Area", 155, 32, "No"), 6, 0);
            gridL.add(createGrid.createColName("Name of Product", 180, 32, "No"), 7, 0);
            gridL.add(createGrid.createColName("Price", 150, 32, "No"), 8, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void setDataGridd() {
        CEOLoI_JPA jp = new CEOLoI_JPA();
        try {
            ObservableList<ListofInv> ls = jp.getInv();
            System.out.println(ls.size());
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                gridL.add(createGrid.setDataLabelCol(No, 45, 26), 0, i + 1);
                gridL.add(createGrid.setDataLabelCol(String.valueOf(ls.get(i).getId()), 45, 26), 1, i + 1);
                gridL.add(createGrid.setDataLabelCol(ls.get(i).getFullname(), 195, 26), 2, i + 1);
                gridL.add(createGrid.setDataLabelCol(String.valueOf(ls.get(i).getSdt()), 150, 26), 3, i + 1);
                gridL.add(createGrid.setDataLabelCol(ls.get(i).getEmail(), 180, 26), 4, i + 1);
                gridL.add(createGrid.setDataLabelCol(ls.get(i).getTenduan(), 180, 26), 5, i + 1);
                gridL.add(createGrid.setDataLabelCol(ls.get(i).getTenkhu(), 155, 26), 6, i + 1);
                gridL.add(createGrid.setDataLabelCol(ls.get(i).getTensp(), 180, 26), 7, i + 1);
                gridL.add(createGrid.setDataLabelCol(String.valueOf(ls.get(i).getDongia()), 150, 26), 8, i + 1);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void refeshEmpPane() {
        try {
            gridL.getChildren().clear();
            showHeaderRoww();
            setDataGridd();

        } catch (Exception e) {
            System.out.println("ERROR refeshEmpPane");
            System.out.println(e.getMessage());
        }
    }

    private void showAlertWithHeaderText(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Results:");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
