/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.AlertHandling;
import com.bds.pro.model.ChuyenNhuong_KhachHang;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.KhachHang;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author thuan
 */
public class ChuyenNhuongController implements Initializable {
@FXML private AnchorPane ChuyenNhuong;
@FXML private MFXComboBox<ChuyenNhuong_KhachHang> CN_ChonHD;
@FXML private MFXTextField CN_CMND,CN_DiaChi,CN_GiaCN,CN_GiaCNBangChu,CN_HoKhau,CN_MaNV,CN_NoiCap,CN_Ten,CN_MaHD,CN_MaMuaBan,CN_FileHD;
@FXML private MFXDatePicker CN_NgayCap,CN_NgayLap,CN_NgaySinh;

    ObservableList<ChuyenNhuong_KhachHang> mb = DBConnection.getInstance().getChuyenNhuong_KhachHang();
    AlertHandling alert = new AlertHandling();
    File fileChoose;
    KhachHang kh = ValueCheck.KhachHang;
    HashMap<String, Object> hm = new HashMap<>();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    int idkh = 0;
    String mahdmb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(kh!=null){
            idkh = kh.getId();
            CN_Ten.setText(kh.getFullname());
            CN_NgaySinh.setValue(kh.getNgaysinh());
            CN_CMND.setText(kh.getSocmnd());
            CN_NgayCap.setValue(kh.getNgaycap());
            CN_NoiCap.setText(kh.getNoicap());
            CN_HoKhau.setText(kh.getHokhau());
            CN_DiaChi.setText(kh.getAddress());
            hm.put("BTen", kh.getFullname());
            hm.put("BNamSinh", dateFormat.format(kh.getNgaysinh()));
            hm.put("BCMND", kh.getSocmnd());
            hm.put("BNgayCap", dateFormat.format(kh.getNgaycap()));
            hm.put("BHoKhau", kh.getHokhau());
            hm.put("BDiaChi", kh.getAddress());
            hm.put("BDienThoai", kh.getPhone());
            ValueCheck.KhachHang = null;
        }
        CN_ChonHD.setItems(mb);
        CN_ChonHD.getSelectionModel().selectedItemProperty().addListener(e->{
            int giacn = 0;
            ChuyenNhuong_KhachHang item = (ChuyenNhuong_KhachHang)CN_ChonHD.getSelectedItem();
            mahdmb = item.getMahd();
            JSONArray jarr = new JSONArray(item.getDot_tt());
            for (int i = 0; i < jarr.length(); i++) {
                JSONObject job = jarr.getJSONObject(i);
                if(Integer.valueOf((String)job.get("status")) == 1){
                    giacn += Integer.valueOf((String)job.get("money"));
                }
            }
            CN_GiaCN.setText(String.valueOf(giacn));
            CN_NgayLap.setValue(LocalDate.now());
            CN_MaMuaBan.setText(mahdmb);
            hm.put("ATen", item.getFullname());
            hm.put("ANamSinh", dateFormat.format(item.getNgaysinh()));
            hm.put("ACMND", item.getSocmnd());
            hm.put("ANgayCap", dateFormat.format(item.getNgaycap()));
            hm.put("AHoKhau", item.getHokhau());
            hm.put("ADiaChi", item.getAddress());
            hm.put("ADienThoai", item.getPhone());
            hm.put("GiaCN", CN_GiaCN.getText());
        });
    }

    public boolean ThemChuyenNhuong() throws IOException {
        String giacnbangchu;String mahd;String filehd;int idnv;
        if(!"".equals(checkGiaCNBangChu())){giacnbangchu = checkGiaCNBangChu();}else{return false;}
        if(!"".equals(checkMaHD())){mahd = checkMaHD();}else{return false;}
        if(!"".equals(checkFileHDMB())){filehd = checkFileHDMB();}else{return false;}
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
        
        DBConnection.getInstance().insertHDChuyenNhuong(mahd, idkh, "", CN_GiaCN.getText(), "", true, mahdmb, fileChoose.getName(), idnv);
        alert.showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Insert Success");
        App.setRoot(StringValue.ChuyenNhuong_KhachHang);
        return true;
    }
    
    public boolean InChuyenNhuong() {
        if(!"".equals(checkMaHD())){}else{return false;}
        try {
            hm.put("GiaCNBangChu", CN_GiaCNBangChu.getText());
            JasperDesign jdesign = JRXmlLoader.load(StringValue.ChuyenNhuongJasper);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    private String checkGiaCNBangChu() {
        String bangchu = "";
        try {
            if (CN_GiaCNBangChu.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else if (!CN_GiaCNBangChu.getText().matches("^[a-zA-Z\\s]*$")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Letters");
            }else {
                bangchu = CN_GiaCNBangChu.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR bangchu");
        }
        return bangchu;
    }
    
    private String checkMaHD() {
        String mahd = "";
        try {
            if (CN_MaHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (DBConnection.getInstance().getHDChuyenNhuong_ID(CN_MaHD.getText())) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Contact is Duplicate");
            } else {
                mahd = CN_MaHD.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmahd");
            System.out.println(e.getMessage());
        }
        return mahd;
    }
    
    private String checkFileHDMB() {
        String file = "";
        try {
            if (CN_FileHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Choose File");
            }else {
                file = CN_FileHD.getText();
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
            if (CN_MaNV.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!CN_MaNV.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Not Valid");
            }else if (DBConnection.getInstance().getNhanVien_ID(Integer.valueOf(CN_MaNV.getText())).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Not Have This ID");
            } else {
                manv = Integer.valueOf(CN_MaNV.getText());
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmanv");
            System.out.println(e.getMessage());
        }
        return manv;
    }
    
    public void CN_FileChooser(){
        Stage stage = (Stage)ChuyenNhuong.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn Hợp Đồng");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.pdf");
        fc.getExtensionFilters().add(ext);
        fileChoose = fc.showOpenDialog(stage);
        CN_FileHD.setText(fileChoose.getName());
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
        Stage thisStage = (Stage) CN_NoiCap.getScene().getWindow();
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
