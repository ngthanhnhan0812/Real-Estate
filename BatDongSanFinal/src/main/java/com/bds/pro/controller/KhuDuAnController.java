/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.DuAn;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.Khu;
import com.bds.pro.model.MyDialog;
import com.bds.pro.res.StringValue;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class KhuDuAnController implements Initializable {

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
    MFXTextField tfTen, tfHuong, tfDT;
    @FXML
    Label lbDuan, lbTen, lbHuong, lbDT;
    @FXML
    MFXRadioButton sap, dang, da;
    @FXML
    MFXComboBox cbDuan;
    @FXML
    MFXButton btnThem;
    @FXML
    GridPane grid;

    private boolean Error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ToggleGroup group = new ToggleGroup();
        sap.setToggleGroup(group);
        sap.setSelected(false);
        dang.setToggleGroup(group);
        dang.setSelected(true);
        da.setToggleGroup(group);

        setComBoDuAn();
        ActionInsert();
        ShowTable();
        ShowData();
    }

    private void ActionInsert() {
        try {
            btnThem.setOnAction(eh -> {
                //tenkhu,huong,dientich,idduan,trangthai

                String tenkhu = checkName();
                String huong = checkHuong();
                String dientich = checkDT();

                String idduan = checkDuan();
                DuAn da = new DuAn();
                da = (DuAn) cbDuan.getSelectionModel().getSelectedItem();
                idduan = da.getId();

                Short trangthai;
                if (sap.isSelected() == true) {
                    trangthai = 0;
                } else if (dang.isSelected() == true) {
                    trangthai = 1;
                } else {
                    trangthai = 2;
                }

                checkLabelError();

                //insert 
                if (Error == true) {
                    showAlert(Alert.AlertType.ERROR, "Create Project Area", "Error Create KhuDuAn");
                } else {
                    DBConnection.getInstance().InsertKhuDuAn(tenkhu, huong, dientich, idduan, trangthai);
                    showAlert(Alert.AlertType.CONFIRMATION, "Create Project Area", "Success Create KhuDuAn");
                    grid.getChildren().clear();
                    ShowTable();
                    try {
                        App.setRoot(StringValue.KHUDUAN_PAGE);

                    } catch (IOException ex) {
                        Logger.getLogger(KhuDuAnController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setComBoDuAn() {
        try {
            ObservableList<DuAn> ls = DBConnection.getInstance().showDuAn();
            cbDuan.setItems(ls);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //VALIDATE
    //lbDuan, lbTen, lbHuong, lbDT
    private void checkLabelError() {
        List<Label> ls = new ArrayList<>();
        int countError = 0;
        ls.add(lbDuan);
        ls.add(lbTen);
        ls.add(lbHuong);
        ls.add(lbDT);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getText().isBlank()) {
                countError += 1;
            }
        }
        Error = countError != 4;
    }

    private String checkDuan() {
        String DA = "";
        try {
            if (cbDuan.getText().length() == 0) {
                lbDuan.setText("Please select project");
            } else {
                lbDuan.setText("");
                DA = cbDuan.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkDuan");
            System.out.println(e.getMessage());
        }
        return DA;
    }

    private String checkName() {
        String Name = "";
        try {
            if (tfTen.getText().length() == 0) {
                lbTen.setText("Please enter the name of the project area");
            } else if (tfTen.getText().length() < 10) {
                lbTen.setText("Please enter at least 10 characters");
            } else if (!tfTen.getText().matches("^[a-zA-Z\\s]*$")) {
                lbTen.setText("Only letters are allowed, please try again");
            } else {
                lbTen.setText("");
                Name = tfTen.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkName");
            System.out.println(e.getMessage());
        }
        return Name;
    }

    private String checkHuong() {
        String H = "";
        try {
            if (tfHuong.getText().length() == 0) {
                lbHuong.setText("Please enter direction");
            } else {
                lbHuong.setText("");
                H = tfHuong.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkHuong");
            System.out.println(e.getMessage());
        }
        return H;
    }

    private String checkDT() {
        String DT = "";
        try {
            if (tfDT.getText().length() == 0) {
                lbDT.setText("Please enter the area");
            } else if (!tfDT.getText().matches("\\d+")) {
                lbDT.setText("Only numbers are allowed, please try again");
            } else {
                lbDT.setText("");
                DT = tfDT.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkDT");
            System.out.println(e.getMessage());
        }
        return DT;
    }

    //GRIDPANE
    //tenkhu,huong,dientich,idduan,trangthai
    public static Button btnSTT() {
        Button btn = new Button("No");
        return btn;
    }

    public static Button btnDuan() {
        Button btn = new Button("Name of project");
        return btn;
    }

    public static Button btnTen() {
        Button btn = new Button("Area name");
        return btn;
    }

    public static Button btnHuong() {
        Button btn = new Button("Direction");
        return btn;
    }

    public static Button btnDientich() {
        Button btn = new Button("Area");
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
                    DBConnection.getInstance().changeStatusKhu(ID, Status);
                    refeshGrid();

                });
            } else if ("1".equals(Status)) {
                btn.setText("On Sale");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusKhu(ID, Status);
                    refeshGrid();
                });
            } else {
                btn.setText("Handed Over");
                btn.setStyle("-fx-text-fill:green");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusKhu(ID, Status);
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
            grid.add(gt.cellHeader(btnDuan(), 35, 180), 1, 0);
            grid.add(gt.cellHeader(btnTen(), 35, 180), 2, 0);
            grid.add(gt.cellHeader(btnHuong(), 35, 120), 3, 0);
            grid.add(gt.cellHeader(btnDientich(), 35, 120), 4, 0);
            grid.add(gt.cellHeader(btnTrangthai(), 35, 120), 5, 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ShowData() {
        try {
            GridTable gt = new GridTable();
            ObservableList<Khu> ls = DBConnection.getInstance().showKhu();
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                grid.add(gt.cellData(No, 30, 40), 0, i + 1);
                grid.add(gt.cellData(String.valueOf(ls.get(i).getIdduan()), 35, 180), 1, i + 1);
                grid.add(gt.cellData(ls.get(i).getTenkhu(), 35, 180), 2, i + 1);
                grid.add(gt.cellData(ls.get(i).getHuong(), 35, 120), 3, i + 1);
                grid.add(gt.cellData(ls.get(i).getDientich(), 35, 120), 4, i + 1);
                grid.add(gt.cellButton(createBtn(ls.get(i).getTrangthai(), ls.get(i).getId()), 35, 120), 5, i + 1);
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

    //logout
    public void btnLogout() throws IOException {
        Stage thisStage = (Stage) lbDT.getScene().getWindow();
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

    //ALERT
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
