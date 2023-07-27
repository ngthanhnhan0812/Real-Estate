/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.AlertHandling;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.DatCoc_Khachhang;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.SanPham;
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
public class MuaBanController implements Initializable {
    @FXML private AnchorPane MuaBan;
    @FXML private MFXTextField MB_Ten,MB_CMND,MB_Tai,MB_HoKhau,MB_TinhTrang,
        MB_Dot1,MB_Dot2,MB_Dot3,MB_Dot4,
        MB_MaHD,MB_FileHD,MB_MaNV;
    @FXML private MFXDatePicker MB_NgaySinh,MB_CapNgay,MB_NgayLap,
        MB_TGDot1,MB_TGDot2,MB_TGDot3,MB_TGDot4;
    @FXML private MFXComboBox<SanPham> MB_ChonSP;
        AlertHandling alert = new AlertHandling();
        File fileChoose;
        HashMap<String, Object> hm = new HashMap<>();
        String mahdmb;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        JSONArray jArr = new JSONArray();
        JSONObject jOb1 = new JSONObject();
        JSONObject jOb2 = new JSONObject();
        JSONObject jOb3 = new JSONObject();
        JSONObject jOb4 = new JSONObject();
        
        String hopdong;
        String filehd;
        String tinhtrang;
        int manv;
        LocalDate ngaysinh;
        LocalDate ngaylap;
        LocalDate TGDot1;LocalDate TGDot2;LocalDate TGDot3;LocalDate TGDot4;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!ValueCheck.DatCoc_KhachHang.isEmpty()){
            ObservableList<DatCoc_Khachhang> ls = ValueCheck.DatCoc_KhachHang;
            DatCoc_Khachhang dk = ls.get(0);
            mahdmb = dk.getMahd();
            MB_Ten.setText(dk.getFullname());
            MB_NgaySinh.setValue(dk.getNgaysinh());
            MB_CMND.setText(dk.getSocmnd());
            MB_CapNgay.setValue(dk.getNgaycap());
            MB_Tai.setText(dk.getTai());
            MB_HoKhau.setText(dk.getHokhau());
            hm.put("Ten", dk.getFullname());
            hm.put("NgaySinh", dateFormat.format(dk.getNgaysinh()));
            hm.put("CMND", dk.getSocmnd());
            hm.put("NgayCap", dateFormat.format(dk.getNgaycap()));
            hm.put("NoiCap", dk.getTai());
            hm.put("HoKhau", dk.getHokhau());
        }
        
        SanPham item = DBConnection.getInstance().showSanPham_HDDC(mahdmb);
        Double d = Integer.valueOf(item.getDongia()) * 0.25;
        MB_Dot1.setText(String.format("%.0f", d));
        MB_Dot2.setText(String.format("%.0f", d));
        MB_Dot3.setText(String.format("%.0f", d));
        MB_Dot4.setText(String.format("%.0f", d));
        hm.put("ViTri", item.getVitri());
        hm.put("DienTich", item.getDientich());
        MB_NgayLap.setValue(LocalDate.now());
        MB_MaNV.setText(String.valueOf(ValueCheck.IDNV));
    }    
    
    public boolean ThemMuaBan() throws IOException {
        if(!"".equals(checkTinhTrang())){tinhtrang = checkTinhTrang();}else{return false;}
        if(checkMBTGDot1()!=null){TGDot1 = checkMBTGDot1();}else{return false;}
        if(checkMBTGDot2()!=null){TGDot2 = checkMBTGDot2();}else{return false;}
        if(checkMBTGDot3()!=null){TGDot3 = checkMBTGDot3();}else{return false;}
        if(checkMBTGDot4()!=null){TGDot4 = checkMBTGDot4();}else{return false;}
        if(!"".equals(checkMaHD())){hopdong = checkMaHD();}else{return false;}
        if(checkMBNgayLap()!=null){ngaylap = checkMBNgayLap();}else{return false;}
        if(!"".equals(checkFileHDMB())){filehd = checkFileHDMB();}else{return false;}
        if(checkMaNV()!=0){manv = checkMaNV();}else{return false;}
        
        jOb1.put("date", TGDot1);jOb1.put("money", MB_Dot1.getText());jOb1.put("ID", "1");jOb1.put("status", "0");
        jOb2.put("date", TGDot2);jOb2.put("money", MB_Dot2.getText());jOb2.put("ID", "2");jOb2.put("status",  "0");
        jOb3.put("date", TGDot3);jOb3.put("money", MB_Dot3.getText());jOb3.put("ID", "3");jOb3.put("status",  "0");
        jOb4.put("date", TGDot4);jOb4.put("money", MB_Dot4.getText());jOb4.put("ID", "4");jOb4.put("status", "0");
        jArr.put(jOb1);jArr.put(jOb2);jArr.put(jOb3);jArr.put(jOb4);
        
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
        
        DBConnection.getInstance().insertHDMuaBan(hopdong, ngaylap.toString(), jArr.toString(), tinhtrang, mahdmb, fileChoose.getName(), manv);
        alert.showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Insert Success");
        App.setRoot(StringValue.DatCoc_KhachHang);
        return true;
    }
    
    private String checkTinhTrang() {
        String tinhtrang = "";
        try {
            if (MB_TinhTrang.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input Legal Status");
            } else if (MB_TinhTrang.getText().length() < 5) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Legal Status Is Too Short");
            } else {
                tinhtrang = MB_TinhTrang.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR tinhtrangphaply");
            System.out.println(e.getMessage());
        }
        return tinhtrang;
    }
    
    private String checkMaHD() {
        String mahd = "";
        try {
            if (MB_MaHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!DBConnection.getInstance().getHDMuaBan_ID(MB_MaHD.getText()).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Contact is Duplicate");
            } else {
                mahd = MB_MaHD.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmahd");
            System.out.println(e.getMessage());
        }
        return mahd;
    }
    
    private LocalDate checkMBNgayLap() {
        LocalDate dcbatdau = null;
        try {
            if (MB_NgayLap.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt = MB_NgayLap.getValue();
                LocalDate dt1 = LocalDate.now();
                if (dt.compareTo(dt1) <= -1) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date");
                }else {
                    dcbatdau = MB_NgayLap.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR checkdcbatdau");
            System.out.println(e.getMessage());
        }
        return dcbatdau;
    }
    
    private LocalDate checkMBTGDot1() {
        LocalDate dcbatdau = null;
        try {
            if (MB_TGDot1.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt1 = MB_TGDot1.getValue();
                LocalDate dtNow = LocalDate.now();
                if (dt1.compareTo(dtNow) <= -1) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date 1");
                }else {
                    dcbatdau = MB_TGDot1.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR Check Mb Dot");
            System.out.println(e.getMessage());
        }
        return dcbatdau;
    }
    
    private LocalDate checkMBTGDot2() {
        LocalDate dcbatdau = null;
        try {
            if (MB_TGDot2.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt1 = MB_TGDot1.getValue();
                LocalDate dt2 = MB_TGDot2.getValue();
                if(dt2.compareTo(dt1) <= 0){
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date 2");
                }else {
                    dcbatdau = MB_TGDot2.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR Check Mb Dot");
            System.out.println(e.getMessage());
        }
        return dcbatdau;
    }
    
    private LocalDate checkMBTGDot3() {
        LocalDate dcbatdau = null;
        try {
            if (MB_TGDot3.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt2 = MB_TGDot2.getValue();
                LocalDate dt3 = MB_TGDot3.getValue();
                if(dt3.compareTo(dt2) <= 0){
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date 3");
                }else {
                    dcbatdau = MB_TGDot3.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR Check Mb Dot");
            System.out.println(e.getMessage());
        }
        return dcbatdau;
    }
    
    private LocalDate checkMBTGDot4() {
        LocalDate dcbatdau = null;
        try {
            if (MB_TGDot4.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt3 = MB_TGDot3.getValue();
                LocalDate dt4 = MB_TGDot4.getValue();
                if(dt4.compareTo(dt3) <= 0){
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date 4");
                }else {
                    dcbatdau = MB_TGDot4.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR Check Mb Dot");
            System.out.println(e.getMessage());
        }
        return dcbatdau;
    }
    
    private String checkFileHDMB() {
        String file = "";
        try {
            if (MB_FileHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Choose File");
            }else {
                file = MB_FileHD.getText();
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
            if (MB_MaNV.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!MB_MaNV.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Not Valid");
            }else if (DBConnection.getInstance().getNhanVien_ID(Integer.valueOf(MB_MaNV.getText())).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Not Have This ID");
            } else {
                manv = Integer.valueOf(MB_MaNV.getText());
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmahd");
            System.out.println(e.getMessage());
        }
        return manv;
    }
    
    public boolean InMuaBan(){
        if(checkMBTGDot1()!=null){TGDot1 = checkMBTGDot1();}else{return false;}
        if(checkMBTGDot2()!=null){TGDot2 = checkMBTGDot2();}else{return false;}
        if(checkMBTGDot3()!=null){TGDot3 = checkMBTGDot3();}else{return false;}
        if(checkMBTGDot4()!=null){TGDot4 = checkMBTGDot4();}else{return false;}
        if(!"".equals(checkMaHD())){hopdong = checkMaHD();}else{return false;}
        try {
            hm.put("Dot1", dateFormat.format(MB_TGDot1.getValue()));
            hm.put("Dot2", dateFormat.format(MB_TGDot2.getValue()));
            hm.put("Dot3", dateFormat.format(MB_TGDot3.getValue()));
            hm.put("Dot4", dateFormat.format(MB_TGDot4.getValue()));
            JasperDesign jdesign = JRXmlLoader.load(StringValue.MuaBanJasper);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    public void MB_FileChooser(){
        Stage stage = (Stage)MuaBan.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn Hợp Đồng");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.pdf");
        fc.getExtensionFilters().add(ext);
        fileChoose = fc.showOpenDialog(stage);
        MB_FileHD.setText(fileChoose.getName());
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
        Stage thisStage = (Stage) MB_Ten.getScene().getWindow();
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
