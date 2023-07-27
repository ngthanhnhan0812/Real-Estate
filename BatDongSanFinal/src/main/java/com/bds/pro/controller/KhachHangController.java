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
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
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
public class KhachHangController implements Initializable {

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
    @FXML void chuyennhuong() throws IOException {App.setRoot(StringValue.ChuyenNhuong_KhachHang);}
    @FXML void datcoc() throws IOException {App.setRoot(StringValue.DatCoc);}
    @FXML void thanhly() throws IOException {App.setRoot(StringValue.ThanhLy);}
    @FXML void muaban() throws IOException {App.setRoot(StringValue.DatCoc_KhachHang);}
    @FXML void phuluc() throws IOException {App.setRoot(StringValue.PhuLuc);}
    @FXML
    MFXTextField tfHoTen, tfCMND, tfTai, tfHoKhau, tfDiaChi, tfSdt, tfEmail;

    @FXML
    Label lbTen, lbNgaySinh, lbCMND, lbNgayCap, lbNoiCap, lbHK, lbDc, lbSdt, lbMail;

    @FXML
    MFXDatePicker dpNgaySinh, dpNgayCap;

    @FXML
    MFXRadioButton ckTN, ckHH;

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
        ckTN.setToggleGroup(group);
        ckTN.setSelected(true);
        ckHH.setToggleGroup(group);
        ActionInsert();
        ShowTable();
    }

    //INSERT 
    private void ActionInsert() {
        try {
            btnThem.setOnAction(eh -> {
                //fullname,ngaysinh,phone,email,hokhau,address,socmnd,ngaycap,noicap,phanloai
                String fullname = checkFullName();
                LocalDate ngaysinh = checkBirthday();
                String phone = checkPhone();
                String email = checkEmail();
                String hokhau = checkHoKhau();
                String address = checkDiaChi();
                String socmnd = checkCMND();
                LocalDate ngaycap = checkNgayCap();
                String noicap = checkNoiCap();
                Short phanloai;
                if (ckTN.isSelected() == true) {
                    phanloai = 0;
                } else {
                    phanloai = 1;
                }
                checkLabelError();
                if (!"".equals(email)) {
                    if (DBConnection.getInstance().getEmail(email) == true) {
                        Error = true;
                        lbMail.setText("Email already exists, please use another email");
                    }
                }

                if (!"".equals(socmnd)) {
                    if (DBConnection.getInstance().getCMND(socmnd) == true) {
                        Error = true;
                        lbCMND.setText("ID number already exists");
                    }
                }
                //insert 
                if (Error == true) {
                    showAlert(Alert.AlertType.ERROR, "Create Client", "Error Create Client");
                } else {
                    DBConnection.getInstance().InsertKhachHang(fullname, ngaysinh, phone, email, hokhau, address, socmnd, ngaycap, noicap, phanloai);
                    showAlert(Alert.AlertType.CONFIRMATION, "Create Client", "Success Create Client");
                    grid.getChildren().clear();
                    ShowTable();
                    try {
                        App.setRoot(StringValue.KHACHHANG_PAGE);
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

    //VALIDATE
    private void checkLabelError() {
        List<Label> ls = new ArrayList<>();
        int countError = 0;
        ls.add(lbTen);
        ls.add(lbNgaySinh);
        ls.add(lbCMND);
        ls.add(lbNgayCap);
        ls.add(lbNoiCap);
        ls.add(lbHK);
        ls.add(lbDc);
        ls.add(lbSdt);
        ls.add(lbMail);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getText().isBlank()) {
                countError += 1;
            }
        }
        Error = countError != 9;
    }

    private String checkFullName() {
        String fullName = "";
        try {
            if (tfHoTen.getText().length() == 0) {
                lbTen.setText("Please enter your full name");
            } else if (tfHoTen.getText().length() < 10) {
                lbTen.setText("Please enter at least 10 characters");
            } else if (!tfHoTen.getText().matches("^[a-zA-Z\\s]*$")) {
                lbTen.setText("Only letters are allowed, please try again");
            } else {
                lbTen.setText("");
                fullName = tfHoTen.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkFullName");
            System.out.println(e.getMessage());
        }
        return fullName;
    }

    private LocalDate checkBirthday() {
        LocalDate birthDay = null;
        try {
            if (dpNgaySinh.getValue() == null) {
                lbNgaySinh.setText("Please select date of birth");
            } else {
                LocalDate dt = dpNgaySinh.getValue();
                LocalDate dt1 = LocalDate.parse("1932-01-01");
                LocalDate dt2 = LocalDate.parse("2005-01-01");
                if (dt.compareTo(dt1) < -1) {
                    lbNgaySinh.setText("Please select a date after 1932-01-01");
                } else if (dt.compareTo(dt2) > 1) {
                    lbNgaySinh.setText("Please select a date before 2005-01-01");
                } else {
                    lbNgaySinh.setText("");
                    birthDay = dpNgaySinh.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR checkBirthday");
            System.out.println(e.getMessage());
        }
        return birthDay;
    }

    private String checkPhone() {
        String Phone = "";
        try {
            if (tfSdt.getText().length() == 0) {
                lbSdt.setText("Please enter the phone number");
            } else if (tfSdt.getText().length() < 10) {
                lbSdt.setText("Please enter at least 10 numbers");
            } else if (!tfSdt.getText().matches("\\d+")) {
                lbSdt.setText("Only numbers are allowed, please try again");
            } else {
                lbSdt.setText("");
                Phone = tfSdt.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkPhone");
            System.out.println(e.getMessage());
        }
        return Phone;
    }

    private String checkHoKhau() {
        String HoKhau = "";
        try {
            if (tfHoKhau.getText().length() == 0) {
                lbHK.setText("Please enter your household");
            } else if (tfHoKhau.getText().length() < 10) {
                lbHK.setText("Please enter at least 10 characters");
            } else {
                lbHK.setText("");
                HoKhau = tfHoKhau.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkHoKhau");
            System.out.println(e.getMessage());
        }
        return HoKhau;
    }

    private String checkDiaChi() {
        String Address = "";
        try {
            if (tfDiaChi.getText().length() == 0) {
                lbDc.setText("Please enter your address");
            } else if (tfDiaChi.getText().length() < 5) {
                lbDc.setText("Please enter at least 5 characters");
            } else {
                lbDc.setText("");
                Address = tfDiaChi.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkDiaChi");
            System.out.println(e.getMessage());
        }
        return Address;
    }

    private String checkEmail() {
        String Email = "";
        try {
            if (tfEmail.getText().length() == 0) {
                lbMail.setText("Please enter your email");
            } else if (!tfEmail.getText().matches("[a-z0-9]+@gmail.com")) {
                lbMail.setText("Please enter the correct email e.g. examplemail@gmail.com");
            } else {
                lbMail.setText("");
                Email = tfEmail.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR checkEmail");
        }
        return Email;
    }

    private String checkCMND() {
        String CMND = "";
        try {
            if (tfCMND.getText().length() == 0) {
                lbCMND.setText("Please enter your ID number");
            } else if (tfCMND.getText().length() < 9 || tfCMND.getText().length() > 12) {
                lbCMND.setText("Please enter at least 9 numbers and up to 12 digits");
            } else if (!tfCMND.getText().matches("\\d+")) {
                lbCMND.setText("Only numbers are allowed, please try again");
            } else {
                lbCMND.setText("");
                CMND = tfCMND.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkCMND");
            System.out.println(e.getMessage());
        }
        return CMND;
    }

    private LocalDate checkNgayCap() {
        LocalDate NgayCap = null;
        try {
            if (dpNgayCap.getValue() == null) {
                lbNgayCap.setText("Please select an issue date");
            } else {
                LocalDate dt = dpNgayCap.getValue();
                LocalDate dt1 = LocalDate.parse("1950-01-01");
                LocalDate dt2 = LocalDate.parse("2022-11-07");
                if (dt.compareTo(dt1) < -1) {
                    lbNgayCap.setText("Please select a date after 1932-01-01");
                } else if (dt.compareTo(dt2) > 1) {
                    lbNgayCap.setText("Please select a date before 2022-11-07");
                } else {
                    lbNgayCap.setText("");
                    NgayCap = dpNgayCap.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR checkNgayCap");
            System.out.println(e.getMessage());
        }
        return NgayCap;
    }

    private String checkNoiCap() {
        String NoiCap = "";
        try {
            if (tfTai.getText().length() == 0) {
                lbNoiCap.setText("Please enter the place of issue of ID card");
            } else if (tfTai.getText().length() < 5) {
                lbNoiCap.setText("Please enter at least 5 characters");
            } else {
                lbNoiCap.setText("");
                NoiCap = tfTai.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkNoiCap");
            System.out.println(e.getMessage());
        }
        return NoiCap;
    }

    //GRIDPANE
    //No,fullname,phone,emailphanloai
    public static Button btnSTT() {
        Button btn = new Button("No");
        return btn;
    }

    public static Button btnHoTen() {
        Button btn = new Button("Full name");
        return btn;
    }

    public static Button btnSdt() {
        Button btn = new Button("Phone");
        return btn;
    }

    public static Button btnMail() {
        Button btn = new Button("Email");
        return btn;
    }

    public static Button btnPhanLoai() {
        Button btn = new Button("Customer Type");
        return btn;
    }
    
    public static Button btnHDDC() {
        Button btn = new Button("Action");
        return btn;
    }

    private void ShowTable() {
        try {
            GridTable gt = new GridTable();
            //No,fullname,ngaysinh,phone,email,address,phanloai
            grid.add(gt.cellHeader(btnSTT(), 35, 40), 0, 0);
            grid.add(gt.cellHeader(btnHoTen(), 35, 150), 1, 0);
            grid.add(gt.cellHeader(btnSdt(), 35, 120), 2, 0);
            grid.add(gt.cellHeader(btnMail(), 35, 150), 3, 0);
            grid.add(gt.cellHeader(btnPhanLoai(), 35, 120), 4, 0);
            grid.add(gt.cellHeader(btnHDDC(), 35, 70), 5, 0);
            ObservableList<KhachHang> ls = DBConnection.getInstance().showKhachHang();
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                grid.add(gt.cellData(No, 30, 40), 0, i + 1);
                grid.add(gt.cellData(ls.get(i).getFullname(), 30, 150), 1, i + 1);
                grid.add(gt.cellData(ls.get(i).getPhone(), 30, 120), 2, i + 1);
                grid.add(gt.cellData(ls.get(i).getEmail(), 30, 150), 3, i + 1);
                grid.add(gt.cellData(String.valueOf(ls.get(i).getPhanloai()), 35, 120), 4, i + 1);
                grid.add(gt.cellButton(createBtn(ls.get(i)), 30, 70), 5, i+1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
     public Button createBtn (KhachHang kh){ 
        Button btn = new Button("HDDC");
        try {
            btn.setOnAction(eh->{
                ValueCheck.KhachHang = kh;
                try {
                    App.setRoot(StringValue.DatCoc);
                } catch (IOException ex) {
                    System.out.println("thuan");
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return btn;
    }
    
     public void btnLogout() throws IOException {
        Stage thisStage = (Stage) tfCMND.getScene().getWindow();
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
