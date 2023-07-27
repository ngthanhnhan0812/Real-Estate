/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.model.ListofCus;
import com.bds.pro.model.createGrid;
import com.bds.pro.model.jpa_controller.CEOLoC_JPA;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ntnha
 */
public class CEOLoCController implements Initializable {

    @FXML
    GridPane grid;

    CEOLoC_JPA jp = new CEOLoC_JPA();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
        showHeaderRow();
        setDataGrid();
    }

    private void showHeaderRow() {
        try {
            grid.add(createGrid.createColName("No", 45, 32, "No"), 0, 0);
            grid.add(createGrid.createColName("ID", 45, 32, "No"), 1, 0);
            grid.add(createGrid.createColName("Full Name", 195, 32, "No"), 2, 0);
            grid.add(createGrid.createColName("Phone", 140, 32, "No"), 3, 0);
            grid.add(createGrid.createColName("Email", 180, 32, "No"), 4, 0);
            grid.add(createGrid.createColName("Name of Product", 180, 32, "No"), 5, 0);
            grid.add(createGrid.createColName("Area", 165, 32, "No"), 6, 0);
            grid.add(createGrid.createColName("Time", 131, 32, "No"), 7, 0);
            grid.add(createGrid.createColName("Price", 150, 32, "No"), 8, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setDataGrid() {
        CEOLoC_JPA jp = new CEOLoC_JPA();
        try {
            ObservableList<ListofCus> ls = jp.getCus();
            System.out.println(ls.size());
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                grid.add(createGrid.setDataLabelCol(No, 45, 26), 0, i + 1);
                grid.add(createGrid.setDataLabelCol(String.valueOf(ls.get(i).getId()), 45, 26), 1, i + 1);
                grid.add(createGrid.setDataLabelCol(ls.get(i).getFullname(), 195, 26), 2, i + 1);
                grid.add(createGrid.setDataLabelCol(String.valueOf(ls.get(i).getPhone()), 140, 26), 3, i + 1);
                grid.add(createGrid.setDataLabelCol(ls.get(i).getEmail(), 180, 26), 4, i + 1);
                grid.add(createGrid.setDataLabelCol(ls.get(i).getTensp(), 180, 26), 5, i + 1);
                grid.add(createGrid.setDataLabelCol(ls.get(i).getTenkhu(), 165, 26), 6, i + 1);
                grid.add(createGrid.setDataLabelCol(String.valueOf(ls.get(i).getThoigian()), 131, 26), 7, i + 1);
                grid.add(createGrid.setDataLabelCol(String.valueOf(ls.get(i).getDongia()), 150, 26), 8, i + 1);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void refeshEmpPane() {
        try {
            grid.getChildren().clear();
            showHeaderRow();
            setDataGrid();

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
