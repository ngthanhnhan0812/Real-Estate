/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.DonViThiCong;
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
public class DonViThiCongController implements Initializable {

    @FXML
    MFXTextField tfDVTC, tfTen, tfSoluong, tfSdt, tfDiachi;

    @FXML
    Label lbID, lbTen, lbSoLuong, lbSdt, lbDc;

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
                //Click
                String MaDV = checkID();
                String Ten = checkName();
                String Soluong = checkSoLuong();
                String Diachi = checkDiaChi();
                String Sdt = checkPhone();
                try {
                    fi.writeImage(file);
                } catch (IOException ex) {
                    Logger.getLogger(DonViThiCongController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String Logo = file.getName();

                //insert
                checkLabelError();
                if (Error == true) {
                    showAlert(Alert.AlertType.ERROR, "Create CONSTRUCTION UNIT", "Error!!");
                } else {
                    DBConnection.getInstance().InsertDVThiCong(MaDV, Ten, Logo, Soluong, Diachi, Sdt);
                    showAlert(Alert.AlertType.CONFIRMATION, "Create CONSTRUCTION UNIT", "Successfully!!");
                    grid.getChildren().clear();
                    ShowTable();
                    try {
                        App.setRoot(StringValue.DONVITHICONG_PAGE);
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

    public void ThemDVThiCong() {

    }

    public File ImageFileChooser(Stage stage) {

        return file;
    }

    //ALERT
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    //VALIDATE
    private void checkLabelError() {
        List<Label> ls = new ArrayList<>();
        int countError = 0;
        ls.add(lbID);
        ls.add(lbTen);
        ls.add(lbSoLuong);
        ls.add(lbSdt);
        ls.add(lbDc);
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i).getText().isBlank()) {
                countError += 1;
            }
        }
        Error = countError != 5;
    }

    private String checkID() {
        String ID = "";
        try {
            if (tfDVTC.getText().length() == 0) {
                lbID.setText("Unit code cannot be left blank");
            } else {
                lbID.setText("");
                ID = tfDVTC.getText();
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
                lbTen.setText("Please enter the name of the construction unit");
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

    private String checkSoLuong() {
        String SoLuong = "";
        try {
            if (tfSoluong.getText().length() == 0) {
                lbSoLuong.setText("Please enter quantity");
            } else if (!tfSoluong.getText().matches("\\d+")) {
                lbSoLuong.setText("Only numbers are allowed, please try again");

            } else {
                lbSoLuong.setText("");
                SoLuong = tfSoluong.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkSoLuong");
            System.out.println(e.getMessage());
        }
        return SoLuong;
    }

    private String checkDiaChi() {
        String Address = "";
        try {
            if (tfDiachi.getText().length() == 0) {
                lbDc.setText("Please enter your address");
            } else if (tfDiachi.getText().length() < 5) {
                lbDc.setText("Please enter at least 5 characters");
            } else {
                lbDc.setText("");
                Address = tfDiachi.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkDiaChi");
            System.out.println(e.getMessage());
        }
        return Address;
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

    //GRIDPANE
    public static Button btnSTT() {
        Button btn = new Button("No");
        return btn;
    }

    public static Button btnTen() {
        Button btn = new Button("Unit name");
        return btn;
    }

    public static Button btnSoLuong() {
        Button btn = new Button("Number of construction projects");
        return btn;
    }

    public static Button btnDiaChi() {
        Button btn = new Button("Address");
        return btn;
    }

    public static Button btnSDT() {
        Button btn = new Button("Phone");
        return btn;
    }

    private void ShowTable() {
        try {
            GridTable gt = new GridTable();
            //ten,soluong,diachi,sdt
            grid.add(gt.cellHeader(btnSTT(), 35, 40), 0, 0);
            grid.add(gt.cellHeader(btnTen(), 35, 200), 1, 0);
            grid.add(gt.cellHeader(btnSoLuong(), 35, 220), 2, 0);
            grid.add(gt.cellHeader(btnDiaChi(), 35, 150), 3, 0);
            grid.add(gt.cellHeader(btnSDT(), 35, 120), 4, 0);
            ObservableList<DonViThiCong> ls = DBConnection.getInstance().showDVThiCong();
            System.out.println(ls.size());
            for (int i = 0; i < ls.size(); i++) {
                String STT = String.valueOf(i);
                grid.add(gt.cellData(STT, 30, 40), 0, i + 1);
                grid.add(gt.cellData(ls.get(i).getTen(), 30, 200), 1, i + 1);
                grid.add(gt.cellData(String.valueOf(ls.get(i).getSoluong()), 35, 220), 2, i + 1);
                grid.add(gt.cellData(ls.get(i).getDiachi(), 30, 150), 3, i + 1);
                grid.add(gt.cellData(ls.get(i).getSdt(), 30, 120), 4, i + 1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void btnLogout() throws IOException {
        Stage thisStage = (Stage) lbDc.getScene().getWindow();
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
}
