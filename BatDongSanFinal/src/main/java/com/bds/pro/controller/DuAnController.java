/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.ChuDauTu;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.DonViThiCong;
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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class DuAnController implements Initializable {

    @FXML
    MFXTextField tfID, tfTen, tfGT, tfMB, tfVT, tfDT, tfPL, tfDVQL, tfMD, tfTI;
    @FXML
    Label lbID, lbTen, lbGT, lbMB, lbVT, lbCDT, lbDT, lbPL, lbDVTC, lbDVQL, lbMD, lbTI;
    @FXML
    MFXRadioButton sap, dang, da;
    @FXML
    MFXComboBox cbChudautu, cbDVthicong;
    @FXML
    MFXButton btnThem;

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

        setComboChuDauTu();
        setComboDonViThiCong();
        ActionInsert();
    }

    //INSERT
    private void ActionInsert() {
        try {
            btnThem.setOnAction(eh -> {
                //id,tenduan,gtduan,matban,vitri,dientich,phaply,matdo,tienich,idchudautu,iddvthicong,dvquanly,trangthai

                String id = checkID();
                String tenduan = checkName();
                String gtduan = checkGioiThieu();
                String matbang = checkMatBang();
                String vitri = checkVT();
                String dientich = checkDT();
                String phaply = checkPL();
                String matdo = checkMD();
                String tienich = checkTI();
                String dvquanly = checkDVQL();

                String idchudautu = checkCDT();
                String iddvthicong = checkDVTC();

                ChuDauTu cdt = new ChuDauTu();
                cdt = (ChuDauTu) cbChudautu.getSelectionModel().getSelectedItem();
                idchudautu = cdt.getId();

                DonViThiCong cvtc = new DonViThiCong();
                cvtc = (DonViThiCong) cbDVthicong.getSelectionModel().getSelectedItem();
                iddvthicong = cvtc.getId();

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
                    showAlert(Alert.AlertType.ERROR, "Create Project", "Error Create Project");
                } else {
                    DBConnection.getInstance().InsertDuAn(id, tenduan, gtduan, matbang, vitri, dientich, phaply, matdo, tienich, idchudautu, iddvthicong, dvquanly, trangthai);
                    showAlert(Alert.AlertType.CONFIRMATION, "Create Project", "Success Create Project");
                }
            }
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setComboChuDauTu() {
        try {
            ObservableList<ChuDauTu> ls = DBConnection.getInstance().showChuDauTu();
            cbChudautu.setItems(ls);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setComboDonViThiCong() {
        try {
            ObservableList<DonViThiCong> lss = DBConnection.getInstance().showDVThiCong();
            cbDVthicong.setItems(lss);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //VALIDATE
    //lbID,lbTen,lbGT, lbMB, lbVT,lbCDT, lbDT, lbPL,lbDVTC, lbDVQL, lbMD, lbTI
    private void checkLabelError() {
        List<Label> ls = new ArrayList<>();
        int countError = 0;
        ls.add(lbID);
        ls.add(lbTen);
        ls.add(lbGT);
        ls.add(lbMB);
        ls.add(lbVT);
        ls.add(lbCDT);
        ls.add(lbDT);
        ls.add(lbPL);
        ls.add(lbDVTC);
        ls.add(lbDVQL);
        ls.add(lbMD);
        ls.add(lbTI);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getText().isBlank()) {
                countError += 1;
            }
        }
        Error = countError != 12;
    }

    private String checkID() {
        String ID = "";
        try {
            if (tfID.getText().length() == 0) {
                lbID.setText("Project code cannot be empty");
            } else {
                lbID.setText("");
                ID = tfID.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkID");
            System.out.println(e.getMessage());
        }
        return ID;
    }

    private String checkName() {
        String Name = "";
        try {
            if (tfTen.getText().length() == 0) {
                lbTen.setText("Please enter project name");
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

    private String checkGioiThieu() {
        String GT = "";
        try {
            if (tfGT.getText().length() == 0) {
                lbGT.setText("Please enter description");
            } else if (tfGT.getText().length() < 10) {
                lbTen.setText("Please enter at least 10 characters");

            } else {
                lbGT.setText("");
                GT = tfGT.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkGT");
            System.out.println(e.getMessage());
        }
        return GT;
    }

    private String checkMatBang() {
        String MB = "";
        try {
            if (tfMB.getText().length() == 0) {
                lbMB.setText("Please enter the project site");
            } else if (!tfMB.getText().matches("\\d+")) {
                lbMB.setText("Only numbers are allowed, please try again");
            }else {
                lbMB.setText("");
                MB = tfMB.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkMB");
            System.out.println(e.getMessage());
        }
        return MB;
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

    private String checkCDT() {
        String CDT = "";
        try {
            if (cbChudautu.getText().length() == 0) {
                lbCDT.setText("Please choose an investor");
            } else {
                lbCDT.setText("");
                CDT = cbChudautu.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkCDT");
            System.out.println(e.getMessage());
        }
        return CDT;
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

    private String checkPL() {
        String PL = "";
        try {
            if (tfPL.getText().length() == 0) {
                lbPL.setText("Please enter legal");
            } else {
                lbPL.setText("");
                PL = tfPL.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkPL");
            System.out.println(e.getMessage());
        }
        return PL;
    }

    private String checkDVTC() {
        String DVTC = "";
        try {
            if (cbDVthicong.getText().length() == 0) {
                lbDVTC.setText("Please choose a construction unit");
            } else {
                lbDVTC.setText("");
                DVTC = cbDVthicong.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkDVTC");
            System.out.println(e.getMessage());
        }
        return DVTC;
    }

    private String checkDVQL() {
        String dvql = "";
        try {
            if (tfDVQL.getText().length() == 0) {
                lbDVQL.setText("Please enter the management unit");
            } else {
                lbDVQL.setText("");
                dvql = tfDVQL.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkDVQL");
            System.out.println(e.getMessage());
        }
        return dvql;
    }

    private String checkMD() {
        String MD = "";
        try {
            if (tfMD.getText().length() == 0) {
                lbMD.setText("Please enter building density");
            } else {
                lbMD.setText("");
                MD = tfMD.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkMD");
            System.out.println(e.getMessage());
        }
        return MD;
    }

    private String checkTI() {
        String TI = "";
        try {
            if (tfTI.getText().length() == 0) {
                lbTI.setText("Please enter the widget");
            } else {
                lbTI.setText("");
                TI = tfTI.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkTI");
            System.out.println(e.getMessage());
        }
        return TI;
    }

    //ALERT
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    //MENU
    public void btnLogout() throws IOException {
        Stage thisStage = (Stage) lbCDT.getScene().getWindow();
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

    public void khachhang() throws IOException {
        App.setRoot(StringValue.KHACHHANG_PAGE);
    }

    public void dvthicong() throws IOException {
        App.setRoot(StringValue.DONVITHICONG_PAGE);
    }

    public void chudautu() throws IOException {
        App.setRoot(StringValue.CHUDAUTU_PAGE);
    }

    public void duan() throws IOException {
        App.setRoot(StringValue.DUAN_PAGE);
    }

    public void xemduan() throws IOException {
        App.setRoot(StringValue.XEMDUAN_PAGE);
    }

    public void khuduan() throws IOException {
        App.setRoot(StringValue.KHUDUAN_PAGE);
    }

    public void sanpham() throws IOException {
        App.setRoot(StringValue.SANPHAM_PAGE);
    }

    public void datcoc() throws IOException {
        App.setRoot(StringValue.DatCoc);
    }

    public void muaban() throws IOException {
        App.setRoot(StringValue.DatCoc_KhachHang);
    }

    public void chuyennhuong() throws IOException {
        App.setRoot(StringValue.ChuyenNhuong_KhachHang);
    }

    
    public void phuluc() throws IOException {
        App.setRoot(StringValue.PhuLuc);
    }

    public void thanhly() throws IOException {
        App.setRoot(StringValue.ThanhLy);
    }

}
