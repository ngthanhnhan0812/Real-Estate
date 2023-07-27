/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.AlertHandling;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.KhachHang;
import com.bds.pro.model.MyDialog;
import com.bds.pro.model.SanPham;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import com.lowagie.text.DocumentException;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
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
public class DatCocController implements Initializable {
    @FXML AnchorPane DatCoc;
    @FXML private MFXTextField A_CMND,A_DiaChi,A_DienThoai,A_Email,A_HoKhau,A_Tai,A_Ten,
        DC_GiaDC,DC_GiaDCBangChu,DC_ThoiHan,DC_ViTriSP,DC_DienTichSP,DC_GiaSP,
        DC_MaHD,DC_FileDC,DC_MaNV;
    @FXML private MFXDatePicker A_NamSinh,A_CapNgay,DC_BatDau,DC_NgayLap;
    @FXML private MFXComboBox<SanPham> DC_ChonSP;

    AlertHandling alert = new AlertHandling();
    File fileChoose;
    ObservableList<SanPham> sp = DBConnection.getInstance().showSanPham_TrangThai();
    KhachHang kh = ValueCheck.KhachHang;
    int idsp;
    String fullname;
    HashMap<String, Object> hm = new HashMap<>();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    
    LocalDate ngaysinh;
    String phone;
    String email;
    String address;
    String socmnd;
    LocalDate ngaycap;
    String noicap;
    String hokhau;
    String giadc;
    String giadcbangchu;
    String thoihandc;
    LocalDate batdaudc;
    String mahd;
    String filedc;
    int idnv;
    int idkh = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(kh!=null){
            ObservableList<KhachHang> ob = DBConnection.getInstance().getKhachHang_ID(kh.getId());
            A_CMND.setText(ob.get(0).getSocmnd());
            A_CapNgay.setValue(ob.get(0).getNgaycap());
            A_DiaChi.setText(ob.get(0).getAddress());
            A_DienThoai.setText(ob.get(0).getPhone());
            A_Email.setText(ob.get(0).getEmail());
            A_HoKhau.setText(ob.get(0).getHokhau());
            A_NamSinh.setValue(ob.get(0).getNgaysinh());
            A_Tai.setText(ob.get(0).getNoicap());
            A_Ten.setText(ob.get(0).getFullname());
            hm.put("NgayCap", dateFormat.format(ob.get(0).getNgaycap()));
            hm.put("CMND", ob.get(0).getSocmnd());
            hm.put("NgaySinh", dateFormat.format(ob.get(0).getNgaysinh()));
            hm.put("Ten", ob.get(0).getFullname());
            hm.put("HoKhau", ob.get(0).getHokhau());
            hm.put("NoiCap", ob.get(0).getNoicap());
        }
        DC_ChonSP.setItems(sp);
        DC_ChonSP.getSelectionModel().selectedItemProperty().addListener(e->{
            SanPham item = (SanPham)DC_ChonSP.getSelectedItem();
            Double d = Integer.valueOf(item.getDongia())*0.1;
            idsp = item.getId();
            DC_GiaDC.setText(String.format("%.0f", d));
            DC_ViTriSP.setText(item.getVitri());
            DC_DienTichSP.setText(item.getDientich());
            DC_GiaSP.setText(item.getDongia());
            hm.put("DienTich", item.getDientich());
            hm.put("ViTri", item.getVitri());
            hm.put("GiaSP", item.getDongia());
        });
        DC_NgayLap.setValue(LocalDate.now());
        DC_MaNV.setText(String.valueOf(ValueCheck.IDNV));
    }
    
    public boolean ThemDatCoc() throws FileNotFoundException, DocumentException, IOException {
        if(!"".equals(checkGiaDC())){giadc = checkGiaDC();}else{return false;}
        if(!"".equals(checkGiaDCBangChu())){giadcbangchu = checkGiaDCBangChu();}else{return false;}
        if(!"".equals(checkThoiHanDC())){thoihandc = checkThoiHanDC();}else{return false;}
        if(checkDCBatDau()!=null){batdaudc = checkDCBatDau();}else{return false;}
        if(!"".equals(checkMaHD())){mahd = checkMaHD();}else{return false;}
        if(!"".equals(checkFileHDDC())){filedc = checkFileHDDC();}else{return false;}
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
        
        if(kh == null){
            if(!"".equals(checkFullName())){fullname = checkFullName();}else{return false;}
            if(checkBirthday()!=null){ngaysinh = checkBirthday();}else{return false;}
            if(!"".equals(checkPhone())){phone = checkPhone();}else{return false;}
            if(!"".equals(checkEmail())){email = checkEmail();}else{return false;}
            if(!"".equals(checkIDCard())){socmnd = checkIDCard();}else{return false;}
            if(checkCapNgay()!=null){ngaycap = checkCapNgay();}else{return false;}
            if(!"".equals(checkTai())){noicap = checkTai();}else{return false;}
            if(!"".equals(checkAddress())){address = checkAddress();}else{return false;}
            if(!"".equals(checkHoKhau())){hokhau = checkHoKhau();}else{return false;}
            DBConnection.getInstance().insertKhachHang(fullname, phone, email, hokhau, address, ngaysinh.toString(), "1", ngaycap.toString(), noicap, socmnd);
            idkh = DBConnection.getInstance().getIDKH_CMND(socmnd);
        }else{
            idkh = kh.getId();
            DBConnection.getInstance().updateKhachHang("KH Hiện Hữu", idkh);
        }
        DBConnection.getInstance().insertHDDC(DC_MaHD.getText(),idkh, giadc, thoihandc,
            batdaudc.toString(), DC_NgayLap.getValue().toString(), idsp, idnv, false, fileChoose.getName());
        DBConnection.getInstance().insertKHMuaBan(idkh, idsp, batdaudc.toString());
        DBConnection.getInstance().updateSanPham("2", idsp);
        
        alert.showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Insert Success");
        App.setRoot(StringValue.DatCoc);
        return true;
    }
    
    private String checkFullName() {
        String fullName = "";
        try {
            if (A_Ten.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input Name");
            } else if (A_Ten.getText().length() < 10) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please enter at least 10 characters");
            } else if (!A_Ten.getText().matches("^[a-zA-Z\\s]*$")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Letters");
            } else {
                fullName = A_Ten.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkFullName");
            System.out.println(e.getMessage());
        }
        return fullName;
    }

    private String checkAddress() {
        String Address = "";
        try {
            if (A_DiaChi.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input Address");
            } else if (A_DiaChi.getText().length() < 10) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please enter at least 10 characters");
            } else {
                Address = A_DiaChi.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkAddress");
            System.out.println(e.getMessage());
        }
        return Address;
    }

    private String checkPhone() {
        String Phone = "";
        try {
            if (A_DienThoai.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (A_DienThoai.getText().length() < 10) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please enter at least 10 number");
            }else if (!A_DienThoai.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }else if (DBConnection.getInstance().getPhone(A_DienThoai.getText())) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Phone Number Is Duplicate");
            }else {
                Phone = A_DienThoai.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkPhone");
            System.out.println(e.getMessage());
        }
        return Phone;
    }

    private LocalDate checkBirthday() {
        LocalDate birthDay = null;
        try {
            if (A_NamSinh.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select BirthDay");
            } else {
                LocalDate dt = A_NamSinh.getValue();
                LocalDate dt1 = LocalDate.parse("1961-01-01");
                LocalDate dt2 = LocalDate.parse("2011-01-01");
                if (dt.compareTo(dt1) < -1) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please select date after 1960-01-01");
                } else if (dt.compareTo(dt2) > 1) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please select date before 2010-01-01");
                } else {
                    birthDay = A_NamSinh.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR checkBirthday");
            System.out.println(e.getMessage());
        }
        return birthDay;
    }

    private String checkEmail() {
        String Email = "";
        try {
            if (A_Email.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!A_Email.getText().matches("[a-z0-9]+@gmail.com")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input Correct Email, example: examplemail@gmail.com");
            } else {
                Email = A_Email.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR checkEmail");
        }
        return Email;
    }
    
    private String checkIDCard() {
        String IDCard = "";
        try {
            if (A_CMND.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (A_CMND.getText().length() < 9 || A_CMND.getText().length() > 12) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid IDCard");
            } else if (!A_CMND.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }  else if (DBConnection.getInstance().getCMND(A_CMND.getText())==true) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Card Number Is Duplicate");
            } else {
                IDCard = A_CMND.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkIDCard");
            System.out.println(e.getMessage());
        }
        return IDCard;
    }
    
    private String checkIDCard2() {
        String IDCard = "";
        try {
            if (A_CMND.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (A_CMND.getText().length() < 9 || A_CMND.getText().length() > 12) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid IDCard");
            } else if (!A_CMND.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }else {
                IDCard = A_CMND.getText();
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR checkIDCard");
            System.out.println(e.getMessage());
        }
        return IDCard;
    }
    
    private LocalDate checkCapNgay() {
        LocalDate checkCapNgay = null;
        try {
            if (A_CapNgay.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt = A_CapNgay.getValue();
                LocalDate dt1 = LocalDate.now();
                if (dt.compareTo(dt1) > 0) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date");
                }else {
                    checkCapNgay = A_CapNgay.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR checkCapNgay");
            System.out.println(e.getMessage());
        }
        return checkCapNgay;
    }
    
    private String checkTai() {
        String Tai = "";
        try {
            if (A_Tai.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else {
                Tai = A_Tai.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR checkTai");
        }
        return Tai;
    }
    
    private String checkHoKhau() {
        String HoKhau = "";
        try {
            if (A_HoKhau.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else {
                HoKhau = A_HoKhau.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR checkHoKhau");
        }
        return HoKhau;
    }
    
    private String checkGiaDC() {
        String giadatcoc = "";
        try {
            if (DC_GiaDC.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else if (!DC_GiaDC.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }else {
                giadatcoc = DC_GiaDC.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR checkGiaDC");
        }
        return giadatcoc;
    }
    
    private String checkGiaDCBangChu() {
        String bangchu = "";
        try {
            if (DC_GiaDCBangChu.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else if (!DC_GiaDCBangChu.getText().matches("^[a-zA-Z\\s]*$")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Letters");
            }else {
                bangchu = DC_GiaDCBangChu.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR bangchu");
        }
        return bangchu;
    }
    
    private LocalDate checkDCBatDau() {
        LocalDate dcbatdau = null;
        try {
            if (DC_BatDau.getValue() == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Select Date");
            } else {
                LocalDate dt = DC_BatDau.getValue();
                LocalDate dt1 = LocalDate.now();
                if (dt.compareTo(dt1) <= -1) {
                    alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Invalid Date");
                }else {
                    dcbatdau = DC_BatDau.getValue();
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR checkdcbatdau");
            System.out.println(e.getMessage());
        }
        return dcbatdau;
    }
    
    private String checkThoiHanDC() {
        String thoihandatcoc = "";
        try {
            if (DC_ThoiHan.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            }else if (!DC_ThoiHan.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "This Field Contains Only Number");
            }else {
                thoihandatcoc = DC_ThoiHan.getText();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR checkthoihandatcoc");
        }
        return thoihandatcoc;
    }
    
    private String checkMaHD() {
        String mahd = "";
        try {
            if (DC_MaHD.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!DBConnection.getInstance().getHDDatCoc_ID(DC_MaHD.getText()).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Contact is Duplicate");
            } else {
                mahd = DC_MaHD.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmahd");
            System.out.println(e.getMessage());
        }
        return mahd;
    }
    
    private String checkFileHDDC() {
        String file = "";
        try {
            if (DC_FileDC.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Choose File");
            }else {
                file = DC_FileDC.getText();
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
            if (DC_MaNV.getText().length() == 0) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please Input This Field");
            } else if (!DC_MaNV.getText().matches("\\d+")) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "ID Not Valid");
            }else if (DBConnection.getInstance().getNhanVien_ID(Integer.valueOf(DC_MaNV.getText())).isEmpty()) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Not Have This ID");
            } else {
                manv = Integer.valueOf(DC_MaNV.getText());
            }
        } catch (Exception e) {
            System.out.println("ERROR checkmanv");
            System.out.println(e.getMessage());
        }
        return manv;
    }
    
    public boolean InDatCoc(){
        if(!"".equals(checkFullName())){}else{return false;}
        if(checkBirthday()!=null){}else{return false;}
        if(!"".equals(checkPhone())){}else{return false;}
        if(!"".equals(checkEmail())){}else{return false;}
        if(!"".equals(checkIDCard2())){}else{return false;}
        if(checkCapNgay()!=null){}else{return false;}
        if(!"".equals(checkTai())){}else{return false;}
        if(!"".equals(checkAddress())){}else{return false;}
        if(!"".equals(checkHoKhau())){}else{return false;}
        if(!"".equals(checkGiaDC())){giadc=checkGiaDC();}else{return false;}
        if(!"".equals(checkGiaDCBangChu())){giadcbangchu=checkGiaDCBangChu();}else{return false;}
        if(!"".equals(checkThoiHanDC())){thoihandc=checkThoiHanDC();}else{return false;}
        if(checkDCBatDau()!=null){batdaudc=checkDCBatDau();}else{return false;}
        if(!"".equals(checkMaHD())){}else{return false;}
        try {
            hm.put("ThoiHan", thoihandc);
            hm.put("GiaDC", giadc);
            hm.put("GiaDCBangChu", giadcbangchu);
            hm.put("BatDau", dateFormat.format(batdaudc));
            JasperDesign jdesign = JRXmlLoader.load(StringValue.DatCocJasper);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    public void DC_FileChooser(){
        Stage stage = (Stage)DatCoc.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn Hợp Đồng");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.pdf");
        fc.getExtensionFilters().add(ext);
        fileChoose = fc.showOpenDialog(stage);
        DC_FileDC.setText(fileChoose.getName());
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
        Stage thisStage = (Stage) A_CMND.getScene().getWindow();
        ValueCheck.DatCoc_KhachHang=null;
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
