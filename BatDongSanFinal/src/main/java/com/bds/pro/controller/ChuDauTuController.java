/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.ChuDauTu;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.FileImages;
import com.bds.pro.model.GridTable;
import com.bds.pro.model.MyDialog;
import com.bds.pro.res.StringValue;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class ChuDauTuController implements Initializable {
    @FXML
    MFXTextField tfTen, tfSap, tfDang, tfDa, tfSdt, tfDiachi, tfEmail, tfGioithieu;

    @FXML
    Label lbTen, lbSap, lbDang, lbDa, lbSdt, lbDC, lbMail, lbGT;

    @FXML
    MFXButton btnThem;

    @FXML
    ImageView imgLogo;

    @FXML
    GridPane grid;

    FileImages fi = new FileImages();
    File file;
    //Stage stage = ;
    String userDirectory = System.getProperty("user.dir");
    String projectPath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\uploads\\";
    String PathSave = userDirectory + projectPath;

    private boolean Error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ActionInsert();
        ShowTable();
    }

    //INSERT 
    private void ActionInsert() {
        try {
            btnThem.setOnAction(eh -> {
                //fullname,logo,sdt,email,diachi,descrip,duansapmo,duandangmo,duanbangiao
                String Fullname = checkFullName();
                String Sdt = checkPhone();
                String Email = checkEmail();
                String Diachi = checkDiaChi();
                String Descrip = checkGioiThieu();
                String Duansapmo = checkSapMo();
                String Duandangmo = checkDangMo();
                String Duanbangiao = checkBanGiao();

                try {
                    fi.writeImage(file);
                } catch (IOException ex) {
                    Logger.getLogger(DonViThiCongController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String Logo = file.getName();

                checkLabelError();
                if (!"".equals(Email)) {
                    if (DBConnection.getInstance().getEmail(Email) == true) {
                        Error = true;
                        lbMail.setText("Email already exists, please use another email");
                    }
                }
                //insert 

                if (Error == true) {
                    showAlert(Alert.AlertType.ERROR, "Create Investor", "Error!!!");
                } else {
                    DBConnection.getInstance().InsertChuDauTu(Fullname, Logo, Sdt, Email, Diachi, Descrip, Duansapmo, Duandangmo, Duanbangiao);
                    showAlert(Alert.AlertType.CONFIRMATION, "Create Investor", "Successfully!!!");
                    grid.getChildren().clear();
                    ShowTable();
                    try {
                        App.setRoot(StringValue.CHUDAUTU_PAGE);
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

    //LOGO
    public void ChooseLogo(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.jpg", "*.png");

        fc.setTitle("Chọn Hình Ảnh");
        fc.getExtensionFilters().add(ext);
        file = fc.showOpenDialog(stage);
        imgLogo.setImage(new Image(file.toURI().toString(), 150, 150, false, true));
    }

    public void ThemChuDauTu() {

    }

    public File ImageFileChooser(Stage stage) {

        return file;
    }

    //VALIDATE
    //fullname,sdt,email,diachi,descrip,duansapmo,duandangmo,duanbangiao
    private void checkLabelError() {
        List<Label> ls = new ArrayList<>();
        int countError = 0;
        ls.add(lbTen);
        ls.add(lbSdt);
        ls.add(lbMail);
        ls.add(lbDC);
        ls.add(lbGT);
        ls.add(lbSap);
        ls.add(lbDang);
        ls.add(lbDa);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getText().isBlank()) {
                countError += 1;
            }
        }
        Error = countError != 8;
    }

    private String checkFullName() {
        String fullName = "";
        try {
            if (tfTen.getText().length() == 0) {
                lbTen.setText("Please enter the owner's name");
            } else if (tfTen.getText().length() < 5) {
                lbTen.setText("Please enter at least 5 characters");
            } else if (!tfTen.getText().matches("^[a-zA-Z\\s]*$")) {
                lbTen.setText("Only letters are allowed, please try again");
            } else {
                lbTen.setText("");
                fullName = tfTen.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkFullName");
            System.out.println(e.getMessage());
        }
        return fullName;
    }

    private String checkPhone() {
        String Phone = "";
        try {
            if (tfSdt.getText().length() == 0) {
                lbSdt.setText("Please enter the phone number");
            } else if (tfSdt.getText().length() < 7) {
                lbSdt.setText("Please enter at least 7 numbers");
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

    private String checkEmail() {
        String Email = "";
        try {
            if (tfEmail.getText().length() == 0) {
                lbMail.setText("Please enter your email");
            } else if (!tfEmail.getText().matches("[a-z0-9]+@gmail.com")) {
                lbMail.setText("Please enter the correct email, e.g: examplemail@gmail.com");
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

    private String checkDiaChi() {
        String Address = "";
        try {
            if (tfDiachi.getText().length() == 0) {
                lbDC.setText("Please enter your address");
            } else if (tfDiachi.getText().length() < 5) {
                lbDC.setText("Please enter at least 5 characters");
            } else {
                lbDC.setText("");
                Address = tfDiachi.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkDiaChi");
            System.out.println(e.getMessage());
        }
        return Address;
    }

    private String checkGioiThieu() {
        String NoiCap = "";
        try {
            if (tfGioithieu.getText().length() == 0) {
                lbGT.setText("Please enter description");
            } else if (tfGioithieu.getText().length() < 10) {
                lbGT.setText("Please enter at least 10 characters");
            } else {
                lbGT.setText("");
                NoiCap = tfGioithieu.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkGioiThieu");
            System.out.println(e.getMessage());
        }
        return NoiCap;
    }

    private String checkSapMo() {
        String Sap = "";
        try {
            if (tfSap.getText().length() == 0) {
                lbSap.setText("Please enter the number of upcoming projects");
            } else if (!tfSap.getText().matches("\\d+")) {
                lbSap.setText("Only numbers are allowed, please try again");
            } else {
                lbSap.setText("");
                Sap = tfSap.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkCMND");
            System.out.println(e.getMessage());
        }
        return Sap;
    }

    private String checkDangMo() {
        String Dang = "";
        try {
            if (tfDang.getText().length() == 0) {
                lbDang.setText("Please enter the number of open projects");
            } else if (!tfDang.getText().matches("\\d+")) {
                lbDang.setText("Only numbers are allowed, please try again");
            } else {
                lbDang.setText("");
                Dang = tfDang.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkDangMo");
            System.out.println(e.getMessage());
        }
        return Dang;
    }

    private String checkBanGiao() {
        String BG = "";
        try {
            if (tfDa.getText().length() == 0) {
                lbDa.setText("Please enter the number of projects handed over");
            } else if (!tfDa.getText().matches("\\d+")) {
                lbDa.setText("Only numbers are allowed, please try again");
            } else {
                lbDa.setText("");
                BG = tfDa.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkBanGiao");
            System.out.println(e.getMessage());
        }
        return BG;
    }

    //ALERT
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    //GRIDPANE
    //fullname,sdt,email,diachi,duansapmo,duandangmo,duanbangiao
    public static Button btnSTT() {
        Button btn = new Button("No");
        return btn;
    }

    public static Button btnTen() {
        Button btn = new Button("Name of Investor");
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

    public static Button btnDiachi() {
        Button btn = new Button("Address");
        return btn;
    }

    public static Button btnDuanSap() {
        Button btn = new Button("Going to sell");
        return btn;
    }

    public static Button btnDuanDang() {
        Button btn = new Button("On sale");
        return btn;
    }

    public static Button btnDuanDa() {
        Button btn = new Button("Handed over");
        return btn;
    }

    private void ShowTable() {
        try {
            GridTable gt = new GridTable();
            //No,fullname,sdt,email,diachi,duansapmo,duandangmo,duanbangiao
            grid.add(gt.cellHeader(btnSTT(), 35, 40), 0, 0);
            grid.add(gt.cellHeader(btnTen(), 35, 200), 1, 0);
            grid.add(gt.cellHeader(btnSdt(), 35, 100), 2, 0);
            grid.add(gt.cellHeader(btnMail(), 35, 150), 3, 0);
            grid.add(gt.cellHeader(btnDiachi(), 35, 110), 4, 0);
            grid.add(gt.cellHeader(btnDuanSap(), 35, 95), 5, 0);
            grid.add(gt.cellHeader(btnDuanDang(), 35, 65), 6, 0);
            grid.add(gt.cellHeader(btnDuanDa(), 35, 95), 7, 0);
            ObservableList<ChuDauTu> ls = DBConnection.getInstance().showChuDauTu();
            for (int i = 0; i < ls.size(); i++) {
                String No = String.valueOf(i);
                grid.add(gt.cellData(No, 30, 40), 0, i + 1);
                grid.add(gt.cellData(ls.get(i).getFullname(), 30, 200), 1, i + 1);
                grid.add(gt.cellData(ls.get(i).getSdt(), 30, 100), 2, i + 1);
                grid.add(gt.cellData(ls.get(i).getEmail(), 30, 150), 3, i + 1);
                grid.add(gt.cellData(ls.get(i).getDiachi(), 30, 105), 4, i + 1);
                grid.add(gt.cellData(String.valueOf(ls.get(i).getDuansapmo()), 30, 95), 5, i + 1);
                grid.add(gt.cellData(String.valueOf(ls.get(i).getDuandangmo()), 30, 65), 6, i + 1);
                grid.add(gt.cellData(String.valueOf(ls.get(i).getDuanbangiao()), 30, 95), 7, i + 1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
