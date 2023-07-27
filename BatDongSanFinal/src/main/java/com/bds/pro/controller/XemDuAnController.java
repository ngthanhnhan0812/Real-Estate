/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.DuAn;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.KhachHang;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class XemDuAnController implements Initializable {

    @FXML
    public void khachhang() throws IOException {
        App.setRoot(StringValue.KHACHHANG_PAGE);
    }

    @FXML
    public void dvthicong() throws IOException {
        App.setRoot(StringValue.DONVITHICONG_PAGE);
    }

    @FXML
    public void chudautu() throws IOException {
        App.setRoot(StringValue.CHUDAUTU_PAGE);
    }

    @FXML
    public void duan() throws IOException {
        App.setRoot(StringValue.DUAN_PAGE);
    }

    @FXML
    public void xemduan() throws IOException {
        App.setRoot(StringValue.XEMDUAN_PAGE);
    }

    @FXML
    public void khuduan() throws IOException {
        App.setRoot(StringValue.KHUDUAN_PAGE);
    }

    @FXML
    public void sanpham() throws IOException {
        App.setRoot(StringValue.SANPHAM_PAGE);
    }

    @FXML
    public void datcoc() throws IOException {
        App.setRoot(StringValue.DatCoc);
    }

    @FXML
    public void muaban() throws IOException {
        App.setRoot(StringValue.DatCoc_KhachHang);
    }

    @FXML
    public void chuyennhuong() throws IOException {
        App.setRoot(StringValue.ChuyenNhuong_KhachHang);
    }

    @FXML
    public void phuluc() throws IOException {
        App.setRoot(StringValue.PhuLuc);
    }

    @FXML
    public void thanhly() throws IOException {
        App.setRoot(StringValue.ThanhLy);
    }
    @FXML
    GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ShowTable();
        ShowData();
    }

    //GRIDPANE
    //tenduan,matban,vitri,dientich,phaply,matdo,idchudautu,iddvthicong,dvquanly,trangthai
    public static Button btnSTT() {
        Button btn = new Button("No");
        return btn;
    }

    public static Button btnTen() {
        Button btn = new Button("Name of project");
        return btn;
    }

    public static Button btnMatbang() {
        Button btn = new Button("Ground");
        return btn;
    }

    public static Button btnVitri() {
        Button btn = new Button("Location");
        return btn;
    }

    public static Button btnDientich() {
        Button btn = new Button("Area");
        return btn;
    }

    public static Button btnPhaply() {
        Button btn = new Button("Juridical");
        return btn;
    }

    public static Button btnMatdo() {
        Button btn = new Button("Density");
        return btn;
    }

    public static Button btnChudautu() {
        Button btn = new Button("Investor");
        return btn;
    }

    public static Button btnDvthicong() {
        Button btn = new Button("Construction unit");
        return btn;
    }

    public static Button btnDvquanly() {
        Button btn = new Button("Management unit");
        return btn;
    }

    public static Button btnTrangthai() {
        Button btn = new Button("Status");
        return btn;
    }

    public Button createBtn(String Status, String ID) {
        Button btn = new Button();
        try {
            //st 1 cs red
            //st 2 osale none
            //st 0 handed green
            if ("0".equals(Status)) {
                btn.setText("Coming Soon");
                btn.setStyle("-fx-text-fill:red");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusDuAN(ID, Status);
                    refeshGrid();

                });
            } else if ("1".equals(Status)) {
                btn.setText("On Sale");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusDuAN(ID, Status);
                    refeshGrid();
                });
            } else {
                btn.setText("Handed Over");
                btn.setStyle("-fx-text-fill:green");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusDuAN(ID, Status);
                    refeshGrid();
                });
            }

            return btn;
        } catch (Exception e) {
            System.out.println("ERROR createCB");
            System.out.println(e.getMessage());
        }
        return btn;
    }

    private void ShowTable() {
        try {
            GridTable gt = new GridTable();
            //No,tenduan,matban,vitri,dientich,phaply,matdo,idchudautu,iddvthicong,dvquanly,trangthai
            grid.add(gt.cellHeader(btnSTT(), 35, 40), 0, 0);
            grid.add(gt.cellHeader(btnTen(), 35, 180), 1, 0);
            grid.add(gt.cellHeader(btnMatbang(), 35, 70), 2, 0);
            grid.add(gt.cellHeader(btnVitri(), 35, 150), 3, 0);
            grid.add(gt.cellHeader(btnDientich(), 35, 110), 4, 0);
            grid.add(gt.cellHeader(btnPhaply(), 35, 110), 5, 0);
            grid.add(gt.cellHeader(btnMatdo(), 35, 70), 6, 0);
            grid.add(gt.cellHeader(btnChudautu(), 35, 150), 7, 0);
            grid.add(gt.cellHeader(btnDvthicong(), 35, 150), 8, 0);
            grid.add(gt.cellHeader(btnDvquanly(), 35, 150), 9, 0);
            grid.add(gt.cellHeader(btnTrangthai(), 35, 110), 10, 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ShowData() {
        try {
            GridTable gt = new GridTable();
            ObservableList<DuAn> ls = DBConnection.getInstance().showDuAn();
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                grid.add(gt.cellData(No, 30, 40), 0, i + 1);
                grid.add(gt.cellData(ls.get(i).getTenduan(), 30, 180), 1, i + 1);
                grid.add(gt.cellData(ls.get(i).getMatban(), 30, 70), 2, i + 1);
                grid.add(gt.cellData(ls.get(i).getVitri(), 30, 150), 3, i + 1);
                grid.add(gt.cellData(ls.get(i).getDientich(), 35, 110), 4, i + 1);
                grid.add(gt.cellData(ls.get(i).getPhaply(), 35, 110), 5, i + 1);
                grid.add(gt.cellData(ls.get(i).getMatdo(), 35, 70), 6, i + 1);
                grid.add(gt.cellData(ls.get(i).getTenchudautu(), 35, 150), 7, i + 1);
                grid.add(gt.cellData(ls.get(i).getTendvthicong(), 35, 150), 8, i + 1);
                grid.add(gt.cellData(ls.get(i).getDvquanly(), 35, 150), 9, i + 1);
                grid.add(gt.cellButton(createBtn(ls.get(i).getTrangthai(), ls.get(i).getId()), 35, 110), 10, i + 1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void refeshGrid() {
        grid.getChildren().clear();
        ShowTable();
        ShowData();
    }

    public void btnLogout() throws IOException {
        Stage thisStage = (Stage) grid.getScene().getWindow();
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
