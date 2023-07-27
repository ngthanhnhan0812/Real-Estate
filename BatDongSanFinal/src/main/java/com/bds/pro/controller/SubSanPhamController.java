/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.model.DBConnection;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.SanPham;
import com.bds.pro.model.ValueCheck;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author thuan
 */
public class SubSanPhamController implements Initializable {
@FXML private GridPane header;
@FXML private GridPane grid;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowTable();
        ShowData();
    }
    
    public static Button btnSTT() {Button btn = new Button("STT");return btn;}
    public static Button btnTen() {Button btn = new Button("Tên sản phẩm");return btn;}
    public static Button btnDongia() {Button btn = new Button("Đơn giá");return btn;}
    public static Button btnDientich() {Button btn = new Button("Diện tích");return btn;}
    public static Button btnSelect() {Button btn = new Button("Hành Động");return btn;}
    
    private void ShowTable() {
        try {
            GridTable gt = new GridTable();
            header.add(gt.cellHeader(btnSTT(), 35, 40), 0, 0);
            header.add(gt.cellHeader(btnTen(), 35, 180), 1, 0);
            header.add(gt.cellHeader(btnDientich(), 35, 150), 2, 0);
            header.add(gt.cellHeader(btnDongia(), 35, 80), 3, 0);
            header.add(gt.cellHeader(btnSelect(), 35, 50), 4, 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ShowData() {
        try {
            GridTable gt = new GridTable();
            ObservableList<SanPham> ls = DBConnection.getInstance().showSanPham_TrangThai();
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i+1);
                grid.add(gt.cellData(No, 35, 40), 0, i + 1);
                grid.add(gt.cellData(ls.get(i).getTensp(), 30, 180), 1, i + 1);
                grid.add(gt.cellData(ls.get(i).getDientich(), 35, 150), 2, i + 1);
                grid.add(gt.cellData(ls.get(i).getDongia(), 35, 80), 3, i + 1);
                grid.add(gt.cellButton(createBtn(ls), 35, 84), 4, i + 1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private Button createBtn(ObservableList<SanPham> ob){
        Button btn = new Button("Chọn");
        btn.setOnAction(eh->{
            ObservableList<SanPham> a = ob;
            ValueCheck.SanPham = ob;
        });
        return btn;
    }
}