/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.Khu;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.SanPham;
import com.bds.pro.res.StringValue;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
public class SanPhamController implements Initializable {

    @FXML
    MFXTextField tfTen, tfDT, tfMT, tfDG, tfVT, tfHuong, tfTDS, tfBDS, tfDC;
    @FXML
    Label lbTen, lbDT, lbMT, lbDG, lbVT, lbHuong, lbTDS, lbBDS, lbDC, lbKhu;
    @FXML
    MFXRadioButton sap, dang, da;
    @FXML
    MFXComboBox cbKhu;
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

        setComboKhu();
        ActionInsert();
        ShowTable();
        ShowData();
    }

    //INSERT
    private void ActionInsert() {
        try {
            btnThem.setOnAction(eh -> {
                //tensp,dientich,mota,dongia,vitri,huong,thuadatso,bandoso,diachi,idkhu,trangthai

                String tenduan = checkName();
                String dientich = checkDT();
                String mota = checkMoTa();
                String dongia = checkDG();
                String vitri = checkVT();
                String huong = checkHuong();
                String thuadatso = checkTDS();
                String bandoso = checkBDS();
                String daichi = checkDiaChi();

                String idkhu = checkKhu();

                Khu k = new Khu();
                k = (Khu) cbKhu.getSelectionModel().getSelectedItem();
                idkhu = k.getId();

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
                    showAlert(Alert.AlertType.ERROR, "Create Product", "Error Create SanPham");
                } else {
                    DBConnection.getInstance().InsertSanPham(tenduan, dientich, mota, dongia, vitri, huong, thuadatso, bandoso, daichi, idkhu, trangthai);
                    showAlert(Alert.AlertType.CONFIRMATION, "Create Product", "Success Create SanPham");
                    grid.getChildren().clear();
                    ShowTable();
                    ShowData();
                    try {
                        App.setRoot(StringValue.SANPHAM_PAGE);
                    } catch (IOException ex) {
                        Logger.getLogger(ex.getMessage());
                    }
                }
            }
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setComboKhu() {
        try {
            ObservableList<Khu> ls = DBConnection.getInstance().showKhu();
            cbKhu.setItems(ls);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //VALIDATE
    //lbTen,lbDT,lbMT,lbDG,lbVT,lbHuong,lbTDS,lbBDS,lbDC,lbKhu
    private void checkLabelError() {
        List<Label> ls = new ArrayList<>();
        int countError = 0;
        ls.add(lbTen);
        ls.add(lbDT);
        ls.add(lbMT);
        ls.add(lbDG);
        ls.add(lbVT);
        ls.add(lbHuong);
        ls.add(lbTDS);
        ls.add(lbBDS);
        ls.add(lbDC);
        ls.add(lbKhu);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getText().isBlank()) {
                countError += 1;
            }
        }
        Error = countError != 10;
    }

    private String checkName() {
        String Name = "";
        try {
            if (tfTen.getText().length() == 0) {
                lbTen.setText("Please enter product name");
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

    private String checkDT() {
        String DT = "";
        try {
            if (tfDT.getText().length() == 0) {
                lbDT.setText("Please enter the area");
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

    private String checkMoTa() {
        String MT = "";
        try {
            if (tfMT.getText().length() == 0) {
                lbMT.setText("Please enter a description");
            } else if (tfMT.getText().length() < 10) {
                lbMT.setText("Please enter at least 10 characters");

            } else {
                lbMT.setText("");
                MT = tfMT.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkMT");
            System.out.println(e.getMessage());
        }
        return MT;
    }

    private String checkDG() {
        String DG = "";
        try {
            if (tfDG.getText().length() == 0) {
                lbDG.setText("Please enter unit price");
            } else if (!tfDG.getText().matches("\\d+")) {
                lbDG.setText("Only numbers are allowed, please try again");
            } else {
                lbDG.setText("");
                DG = tfDG.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkDG");
            System.out.println(e.getMessage());
        }
        return DG;
    }

    private String checkVT() {
        String VT = "";
        try {
            if (tfVT.getText().length() == 0) {
                lbVT.setText("Please enter location");
            } else if (tfVT.getText().length() < 5) {
                lbVT.setText("Please enter at least 5 characters");

            } else {
                lbVT.setText("");
                VT = tfVT.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkVT");
            System.out.println(e.getMessage());
        }
        return VT;
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

    private String checkTDS() {
        String TDS = "";
        try {
            if (tfTDS.getText().length() == 0) {
                lbTDS.setText("Please enter the land plot");
            } else if (!tfTDS.getText().matches("\\d+")) {
                lbTDS.setText("Only numbers are allowed, please try again");
            } else {
                lbTDS.setText("");
                TDS = tfTDS.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkTDS");
            System.out.println(e.getMessage());
        }
        return TDS;
    }

    private String checkBDS() {
        String BDS = "";
        try {
            if (tfBDS.getText().length() == 0) {
                lbBDS.setText("Please enter map sheet no");
            } else if (!tfBDS.getText().matches("\\d+")) {
                lbBDS.setText("Only numbers are allowed, please try again");
            } else {
                lbBDS.setText("");
                BDS = tfBDS.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkBDS");
            System.out.println(e.getMessage());
        }
        return BDS;
    }

    private String checkDiaChi() {
        String Address = "";
        try {
            if (tfDC.getText().length() == 0) {
                lbDC.setText("Please enter your address");
            } else if (tfDC.getText().length() < 5) {
                lbDC.setText("Please enter at least 5 characters");
            } else {
                lbDC.setText("");
                Address = tfDC.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkDiaChi");
            System.out.println(e.getMessage());
        }
        return Address;
    }

    private String checkKhu() {
        String K = "";
        try {
            if (cbKhu.getText().length() == 0) {
                lbKhu.setText("Please select a project area");
            } else {
                lbKhu.setText("");
                K = cbKhu.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkKhu");
            System.out.println(e.getMessage());
        }
        return K;
    }

    //GRIDPANE
    //tensp,dientich,dongia,vitri,huong,diachi,idkhu,trangthai
    public static Button btnSTT() {
        Button btn = new Button("No");
        return btn;
    }

    public static Button btnKhu() {
        Button btn = new Button("Area name");
        return btn;
    }

    public static Button btnTen() {
        Button btn = new Button("Product's name");
        return btn;
    }

    public static Button btnDientich() {
        Button btn = new Button("Area");
        return btn;

    }

    public static Button btnDongia() {
        Button btn = new Button("Unit price");
        return btn;
    }

    public static Button btnVitri() {
        Button btn = new Button("Location");
        return btn;
    }

    public static Button btnHuong() {
        Button btn = new Button("Direction");
        return btn;
    }

    public static Button btnDC() {
        Button btn = new Button("Address");
        return btn;
    }

    public static Button btnTrangthai() {
        Button btn = new Button("Status");
        return btn;
    }

    public Button createBtn(String Status, Integer ID) {
        Button btn = new Button();
        try {
            //st 1 cs red
            //st 2 osale none
            //st 0 handed green
            if ("0".equals(Status)) {
                btn.setText("Coming Soon");
                btn.setStyle("-fx-text-fill:red");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusSP(ID, Status);
                    refeshGrid();

                });
            } else if ("1".equals(Status)) {
                btn.setText("On Sale");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusSP(ID, Status);
                    refeshGrid();
                });
            } else {
                btn.setText("Handed Over");
                btn.setStyle("-fx-text-fill:green");
                btn.setOnAction(eh -> {
                    DBConnection.getInstance().changeStatusSP(ID, Status);
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
            //No,tensp,dientich,dongia,vitri,huong,diachi,idkhu,trangthai
            grid.add(gt.cellHeader(btnSTT(), 35, 40), 0, 0);
            grid.add(gt.cellHeader(btnKhu(), 35, 130), 1, 0);
            grid.add(gt.cellHeader(btnTen(), 35, 120), 2, 0);
            grid.add(gt.cellHeader(btnDientich(), 35, 65), 3, 0);
            grid.add(gt.cellHeader(btnDongia(), 35, 80), 4, 0);
            grid.add(gt.cellHeader(btnVitri(), 35, 135), 5, 0);
            grid.add(gt.cellHeader(btnHuong(), 35, 85), 6, 0);
            grid.add(gt.cellHeader(btnDC(), 35, 100), 7, 0);
            grid.add(gt.cellHeader(btnTrangthai(), 35, 100), 8, 0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ShowData() {
        try {
            GridTable gt = new GridTable();
            //No,tensp,dientich,dongia,vitri,huong,diachi,idkhu,trangthai
            ObservableList<SanPham> ls = DBConnection.getInstance().showSanPham();
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                grid.add(gt.cellData(No, 35, 40), 0, i + 1);
                grid.add(gt.cellData(ls.get(i).getIdkhu(), 35, 130), 1, i + 1);
                grid.add(gt.cellData(ls.get(i).getTensp(), 30, 120), 2, i + 1);
                grid.add(gt.cellData(ls.get(i).getDientich(), 35, 65), 3, i + 1);
                grid.add(gt.cellData(ls.get(i).getDongia(), 35, 80), 4, i + 1);
                grid.add(gt.cellData(ls.get(i).getVitri(), 35, 135), 5, i + 1);
                grid.add(gt.cellData(ls.get(i).getHuong(), 35, 85), 6, i + 1);
                grid.add(gt.cellData(ls.get(i).getDiachi(), 35, 100), 7, i + 1);
                grid.add(gt.cellButton(createBtn(ls.get(i).getTrangthai(), ls.get(i).getId()), 35, 100), 8, i + 1);
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

    //ALERT
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
