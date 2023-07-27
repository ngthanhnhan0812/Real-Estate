/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.AlertHandling;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.MuaBan_KhachHang;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author thuan
 */
public class PhuLucController implements Initializable {
@FXML private AnchorPane PhuLuc;
@FXML private MFXComboBox<MuaBan_KhachHang> PL_ChonHD;
@FXML private MFXTextField PL_Dieu,PL_HD1,PL_HD2,PL_Khoan,PL_MaHD,PL_FileHD,PL_IDNV;
@FXML private MFXDatePicker PL_Ngay1,PL_Ngay2,PL_NgayLap;
@FXML private TextArea PL_Tu,PL_Thanh;
    ObservableList<MuaBan_KhachHang> hdmb = DBConnection.getInstance().getMuaBan_KhachHang();
    AlertHandling alert = new AlertHandling();
    File fileChoose; 
    HashMap<String, Object> hm = new HashMap<>();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    String mahdmuaban;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PL_ChonHD.setItems(hdmb);
        PL_ChonHD.getSelectionModel().selectedItemProperty().addListener(e->{
            MuaBan_KhachHang mb = (MuaBan_KhachHang)PL_ChonHD.getSelectedItem();
            mahdmuaban = mb.getMahd();
            PL_HD1.setText(mb.getMahd());PL_HD2.setText(mb.getMahd());
            PL_Ngay1.setValue(mb.getNgay());
            PL_Ngay2.setValue(mb.getNgay());
            hm.put("Ten", mb.getFullname());
            hm.put("NgaySinh", dateFormat.format(mb.getNgaysinh()));
            hm.put("CMND", mb.getSocmnd());
            hm.put("NgayCap", dateFormat.format(mb.getNgaycap()));
            hm.put("HoKhau", mb.getHokhau());
            hm.put("MaHD", mb.getMahd());
            hm.put("Ngay", dateFormat.format(mb.getNgay()));
        });
        PL_NgayLap.setValue(LocalDate.now());
        PL_IDNV.setText(String.valueOf(ValueCheck.IDNV));
    }    
    
    public boolean ThemPhuLuc() throws IOException {
        String khoan;
        String dieu;
        String tu;
        String thanh;
        String mahd;
        String filehd;
        int idnv;
        LocalDate ngaylap;
        if(!"".equals(checkHD())){thanh=checkHD();}else{return false;}
        if(!"".equals(checkKhoan())){khoan=checkKhoan();}else{return false;}
        if(!"".equals(checkDieu())){dieu=checkDieu();}else{return false;}
        if(!"".equals(checkTu())){tu=checkTu();}else{return false;}
        if(!"".equals(checkThanh())){thanh=checkThanh();}else{return false;}
        if(!"".equals(checkMaHD())){mahd=checkMaHD();}else{return false;}
        if(checkNgayLap()!=null){ngaylap = checkNgayLap();}else{return false;}
        if(!"".equals(checkFileHD())){filehd = checkFileHD();}else{return false;}
        if(checkMaNV()!=0){idnv = checkMaNV();}else{return false;}
        
        String pathProject = System.getProperty("user.dir");
        String filePath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\contact\\";
        File fo = new File(pathProject + filePath);
        File listfile[] = fo.listFiles();
        if(listfile.length != 0){
            for (File file : listfile) {
                if (fileChoose.getName().equals(file.getName())) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "The Following File Already Exists");
                    return false;
                }
            }
            File fileSave = new File(pathProject + filePath + fileChoose.getName());
            FileUtils.copyFile(fileChoose, fileSave);
        }else{
            File fileSave = new File(pathProject + filePath + fileChoose.getName());
            FileUtils.copyFile(fileChoose, fileSave);
        }
        
        DBConnection.getInstance().insertPhuLucHD(mahd, ngaylap.toString(), khoan, dieu, tu, thanh, mahdmuaban,fileChoose.getName(),idnv);
        alert.showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Insert Success");
        App.setRoot(StringValue.PhuLuc);
        return true;
    }
    
    public boolean InPhuLuc() {
        if(!"".equals(checkHD())){}else{return false;}
        if(!"".equals(checkKhoan())){}else{return false;}
        if(!"".equals(checkDieu())){}else{return false;}
        if(!"".equals(checkTu())){}else{return false;}
        if(!"".equals(checkThanh())){}else{return false;}
        if(!"".equals(checkMaHD())){}else{return false;}
        try {
            hm.put("Khoan", PL_Khoan.getText());
            hm.put("Dieu", PL_Dieu.getText());
            hm.put("Tu", PL_Tu.getText());
            hm.put("Thanh", PL_Thanh.getText());
            JasperDesign jdesign = JRXmlLoader.load(StringValue.PhuLucJasper);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    private String checkKhoan() {
        String khoan = "";
        try {
            if (PL_Khoan.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!PL_Khoan.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }else {
                khoan = PL_Khoan.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR khoan");
            System.out.println(e.getMessage());
        }
        return khoan;
    }
    
    private String checkDieu() {
        String dieu = "";
        try {
            if (PL_Dieu.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!PL_Dieu.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }else {
                dieu = PL_Dieu.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR dieu");
            System.out.println(e.getMessage());
        }
        return dieu;
    }
    
    private String checkTu() {
        String tu = "";
        try {
            if (PL_Tu.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else {
                tu = PL_Tu.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR tu");
            System.out.println(e.getMessage());
        }
        return tu;
    }
    
    private String checkThanh() {
        String thanh = "";
        try {
            if (PL_Thanh.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else {
                thanh = PL_Thanh.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR thanh");
            System.out.println(e.getMessage());
        }
        return thanh;
    }
    
    private String checkHD() {
        String hd = "";
        try {
            if (PL_HD1.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Choose A Contact");
            }else {
                hd = PL_HD1.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR hd");
            System.out.println(e.getMessage());
        }
        return hd;
    }
    
    private String checkMaHD() {
        String mahd = "";
        try {
            if (PL_MaHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!DBConnection.getInstance().getPhuLucHD_ID(PL_MaHD.getText()).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Contact is Duplicate");
            } else {
                mahd = PL_MaHD.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmahd");
            System.out.println(e.getMessage());
        }
        return mahd;
    }
    
    private LocalDate checkNgayLap() {
        LocalDate ngaylap = null;
        try {
            if (PL_NgayLap.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt1 = PL_NgayLap.getValue();
                LocalDate dtNow = LocalDate.now();
                if (dt1.compareTo(dtNow) <= -1) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date 1");
                }else {
                    ngaylap = PL_NgayLap.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR Check ngaylap");
            System.out.println(e.getMessage());
        }
        return ngaylap;
    }
    
    private String checkFileHD() {
        String file = "";
        try {
            if (PL_FileHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Choose File");
            }else {
                file = PL_FileHD.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkfiledc");
            System.out.println(e.getMessage());
        }
        return file;
    }
    
    private int checkMaNV() {
        int manv = 0;
        try {
            if (PL_IDNV.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!PL_IDNV.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Not Valid");
            }else if (DBConnection.getInstance().getNhanVien_ID(Integer.valueOf(PL_IDNV.getText())).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Not Have This ID");
            } else {
                manv = Integer.valueOf(PL_IDNV.getText());
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmanv");
            System.out.println(e.getMessage());
        }
        return manv;
    }
    
    public void PL_FileChooser(){
        Stage stage = (Stage)PhuLuc.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn Hợp Đồng");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.pdf");
        fc.getExtensionFilters().add(ext);
        fileChoose = fc.showOpenDialog(stage);
        PL_FileHD.setText(fileChoose.getName());
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
        Stage thisStage = (Stage) PL_Thanh.getScene().getWindow();
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
