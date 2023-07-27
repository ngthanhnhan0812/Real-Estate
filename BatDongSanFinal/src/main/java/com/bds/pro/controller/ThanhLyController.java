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
public class ThanhLyController implements Initializable {
@FXML private AnchorPane ThanhLy;
@FXML private MFXComboBox<MuaBan_KhachHang> TL_ChonHD;
@FXML private MFXTextField TL_Ngay,TL_HD1,TL_HD2,TL_HD3,TL_HDMB,TL_MaHD,TL_FileHD,
    TL_NguoiDuyet,TL_NguoiLap,TL_PhiTL;
@FXML private MFXDatePicker TL_NgayLap;

    AlertHandling alert = new AlertHandling();
    File fileChoose; 
    ObservableList<MuaBan_KhachHang> hdmb = DBConnection.getInstance().getMuaBan_KhachHang();
    HashMap<String, Object> hm = new HashMap<>();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    String mahdmb = "";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TL_ChonHD.setItems(hdmb);
        TL_ChonHD.getSelectionModel().selectedItemProperty().addListener(e->{
            MuaBan_KhachHang item = (MuaBan_KhachHang)TL_ChonHD.getSelectedItem();
            mahdmb = item.getMahd();
            TL_Ngay.setText(item.getNgay().toString());
            TL_HD1.setText(item.getMahd());
            TL_HD2.setText(item.getMahd());
            TL_HD3.setText(item.getMahd());
            
            hm.put("Ten", item.getFullname());
            hm.put("SinhNgay", dateFormat.format(item.getNgaysinh()));
            hm.put("CMND", item.getSocmnd());
            hm.put("NgayCap", dateFormat.format(item.getNgaycap()));
            hm.put("NoiCap", item.getNoicap());
            hm.put("HDMB", item.getMahd());
        });
        TL_NgayLap.setValue(LocalDate.now());
        TL_NguoiLap.setText(String.valueOf(ValueCheck.IDNV));
    }   
    
    public boolean ThemThanhLy() throws IOException{
        String mahd;
        String ngay;
        String phiTL;
        String filehd;
        int idnvtao;
        int idnvduyet;
        if(!"".equals(checkNgay())){ngay=checkNgay();}else{return false;}
        if(!"".equals(checkMaHD())){mahd = checkMaHD();}else{return false;}
        if(checkMaNVTao()!=0){idnvtao = checkMaNVTao();}else{return false;}
        if(checkMaNVDuyet()!=0){idnvduyet = checkMaNVDuyet();}else{return false;}
        if(!"".equals(checkPhiTL())){phiTL = checkPhiTL();}else{return false;}
        if(!"".equals(checkFileHD())){filehd = checkFileHD();}else{return false;}
        
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
        
        DBConnection.getInstance().insertHDThanhLy(mahd, TL_NgayLap.getValue(), idnvtao, idnvduyet, mahdmb,fileChoose.getName());
        alert.showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Insert Success");
        App.setRoot(StringValue.ThanhLy);
        return true;
    }
    
    public boolean InThanhLy() {
        try {
            if(!"".equals(checkNgay())){}else{return false;}
            if(!"".equals(checkMaHD())){}else{return false;}
            JasperDesign jdesign = JRXmlLoader.load(StringValue.ThanhLyJasper);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    private String checkNgay() {
        String ngay = "";
        try {
            if (TL_Ngay.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Choose Contact");
            }else {
                ngay = TL_Ngay.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR ngay");
            System.out.println(e.getMessage());
        }
        return ngay;
    }
    
    private String checkMaHD() {
        String mahd = "";
        try {
            if (TL_MaHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input ID Contact");
            } else if (DBConnection.getInstance().getHDThanhLy_ID(TL_MaHD.getText())) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Contact is Duplicate");
            } else {
                mahd = TL_MaHD.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmahd");
            System.out.println(e.getMessage());
        }
        return mahd;
    }
    
    private int checkMaNVTao() {
        int manv = 0;
        try {
            if (TL_NguoiLap.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!TL_NguoiLap.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Not Valid");
            }else if (DBConnection.getInstance().getNhanVien_ID(Integer.valueOf(TL_NguoiLap.getText())).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Not Have This ID");
            } else {
                manv = Integer.valueOf(TL_NguoiLap.getText());
            }
        } catch (Exception e) {
            System.out.println("ERROR checknguoilap");
            System.out.println(e.getMessage());
        }
        return manv;
    }
    
    private int checkMaNVDuyet() {
        int manv = 0;
        try {
            if (TL_NguoiDuyet.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!TL_NguoiDuyet.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Not Valid");
            }else if (DBConnection.getInstance().getNhanVien_ID(Integer.valueOf(TL_NguoiDuyet.getText())).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Not Have This ID");
            } else {
                manv = Integer.valueOf(TL_NguoiDuyet.getText());
            }
        } catch (Exception e) {
            System.out.println("ERROR checknguoiduyet");
            System.out.println(e.getMessage());
        }
        return manv;
    }
    
    private String checkPhiTL() {
        String phitl = "";
        try {
            if (TL_PhiTL.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else if (!TL_PhiTL.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }else {
                phitl = TL_PhiTL.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR phiTL");
        }
        return phitl;
    }
    
    private String checkFileHD() {
        String file = "";
        try {
            if (TL_FileHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Choose File");
            }else {
                file = TL_FileHD.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkfiledc");
            System.out.println(e.getMessage());
        }
        return file;
    }
    
    public void TL_FileChooser(){
        Stage stage = (Stage)ThanhLy.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn Hợp Đồng");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.pdf");
        fc.getExtensionFilters().add(ext);
        fileChoose = fc.showOpenDialog(stage);
        TL_FileHD.setText(fileChoose.getName());
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
        Stage thisStage = (Stage) TL_HD3.getScene().getWindow();
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
