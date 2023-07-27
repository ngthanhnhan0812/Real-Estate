/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.KhachHang;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thuan
 */
public class ChuyenNhuong_KhachHangController implements Initializable {
@FXML private GridPane Header,Body;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowTable();
    }    
    
    private void ShowTable() {
        try {
            GridTable gt = new GridTable();
            //No,fullname,ngaysinh,phone,email,address,phanloai
            Header.add(gt.cellHeader(new Button("No"), 30, 50), 0, 0);
            Header.add(gt.cellHeader(new Button("Name Client"), 30, 250), 1, 0);
            Header.add(gt.cellHeader(new Button("Date of birth"), 30, 100), 2, 0);
            Header.add(gt.cellHeader(new Button("ID card"), 30, 100), 3, 0);
            Header.add(gt.cellHeader(new Button("Action"), 30, 100), 4, 0);
            ObservableList<KhachHang> ls = DBConnection.getInstance().getKhachHang();
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                Body.add(gt.cellData(No, 30, 50), 0, i + 1);
                Body.add(gt.cellData(ls.get(i).getFullname(), 30, 250), 1, i + 1);
                Body.add(gt.cellData(dateFormat.format(ls.get(i).getNgaysinh()), 30, 100), 2, i + 1);
                Body.add(gt.cellData(ls.get(i).getSocmnd(), 30, 100), 3, i + 1);
                Body.add(gt.cellButton(createBtn(ls.get(i)), 30, 100), 4, i + 1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Button createBtn (KhachHang kh){ 
        Button btn = new Button("HDCN");
        try {
            btn.setOnAction(eh->{
                ValueCheck.KhachHang = kh;
                try {
                    App.setRoot(StringValue.ChuyenNhuong);
                } catch (IOException ex) {
                    System.out.println("thuan");
                    System.out.println(ex.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return btn;
    }
    
    @FXML void chudautu() {setContentPane(StringValue.ChuDauTu);}
    @FXML void chuyennhuong() {setContentPane(StringValue.ChuyenNhuong_KhachHang);}
    @FXML void datcoc(){setContentPane(StringValue.DatCoc);}
    @FXML void duan() {setContentPane(StringValue.DuAn);}
    @FXML void dvthicong() {setContentPane(StringValue.DVThiCong);}
    @FXML void khachhang(){setContentPane(StringValue.KhachHang);}
    @FXML void khuduan() {setContentPane(StringValue.KHUDUAN_PAGE);}
    @FXML void muaban() {setContentPane(StringValue.DatCoc_KhachHang);}
    @FXML void phuluc() {setContentPane(StringValue.PhuLuc);}
    @FXML void sanpham() {setContentPane(StringValue.SanPham);}
    @FXML void thanhly() {setContentPane(StringValue.ThanhLy);}
    @FXML void xemduan() {setContentPane(StringValue.XEMDUAN_PAGE);}
    private void setContentPane(String nameAnchor) {
        try {
            App.setRoot(nameAnchor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void btnLogout() throws IOException {
        Stage thisStage = (Stage) Header.getScene().getWindow();
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
