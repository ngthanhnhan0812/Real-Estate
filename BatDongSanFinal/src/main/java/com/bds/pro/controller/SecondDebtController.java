/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.Batch;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.Recover;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class SecondDebtController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lbNgay, lbThang, lbNam, lbName, lbCMND, lbThanhPho, lbCapNgay, lbDiaChi, lbMaHD, lbDate, lbTienCK, lbTgTT;

    String MaHD;
    String Batch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getContract();
        setDateTime();
        getInformation();
    }

    private void getContract() {
        String[] parts = ValueCheck.SecondDebt.split("-");
        MaHD = parts[0];
        Batch = parts[1];
    }

    private void setDateTime() {
        LocalDate currentdate = LocalDate.now();
        lbNgay.setText(String.valueOf(currentdate.getDayOfMonth()));
        lbThang.setText(String.valueOf(currentdate.getMonthValue()));
        lbNam.setText(String.valueOf(currentdate.getYear()));
    }

    private void getInformation() {
        try {
            ObservableList<Recover> ls = DBConnection.getInstance().GET_RECOVER(MaHD);
            lbName.setText(ls.get(0).getNameClient());
            lbCMND.setText(ls.get(0).getCMND());
            lbCapNgay.setText(ls.get(0).getNgayCap());
            lbThanhPho.setText(ls.get(0).getNoiCap());
            lbDiaChi.setText(ls.get(0).getAddress());
            lbDate.setText(ls.get(0).getNgayKi());
            lbMaHD.setText(MaHD);
            getBatchInfo(ls.get(0).getDotThanhToan());
        } catch (Exception e) {
            System.out.println("ERROR getInformation");
            System.out.println(e.getMessage());
        }
    }

    private void getBatchInfo(String jsonString) {
        ObservableList<Batch> ls = makeListbyJson(jsonString);
        try {
            for (int i = 0; i < ls.size(); i++) {
                if (ls.get(i).getID().equals(Batch)) {
                    lbTienCK.setText(ls.get(i).getMoney());
                    lbTgTT.setText(formatDay(ls.get(i).getDate()));
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR getBatchInfo");
            System.out.println(e.getMessage());
        }
    }

    private ObservableList<Batch> makeListbyJson(String jsonString) {
        ObservableList<Batch> ls = FXCollections.observableArrayList();
        try {
            JSONArray jsonarray = new JSONArray(jsonString);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Batch bt = new Batch();
                bt.setID(jsonobject.getString("ID"));
                bt.setMoney(jsonobject.getString("money"));
                bt.setDate(jsonobject.getString("date"));
                bt.setStatus(jsonobject.getString("status"));
                ls.add(bt);
            }
        } catch (JSONException e) {
            System.out.println("ERROR makeListbyJson");
            System.out.println(e.getMessage());
        }
        return ls;
    }

    private String formatDay(String Date) {
        String dayFormat = "";
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(Date);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
            dayFormat = String.valueOf(dt1.format(date));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return dayFormat;
    }

    public void btnPrint() throws IOException {
        try {
            HashMap hm = new HashMap();
            hm.put("Ngay", lbNgay.getText());
            hm.put("Thang", lbThang.getText());
            hm.put("Nam", lbNam.getText());
            hm.put("Name", lbName.getText());
            hm.put("CMND", lbCMND.getText());
            hm.put("ThanhPho", lbThanhPho.getText());
            hm.put("CapNgay", lbCapNgay.getText());
            hm.put("MaHD", lbMaHD.getText());
            hm.put("Date", lbDate.getText());
            hm.put("TienCK", lbTienCK.getText());
            hm.put("TgTT", lbTgTT.getText());
            hm.put("DiaChi", lbDiaChi.getText());
            //Close Stage
            Stage thisStage = (Stage) lbDate.getScene().getWindow();
            thisStage.close();
            showAlert(Alert.AlertType.INFORMATION, "Print Reminder", "Success Print Reminder");

            //Jasper
            JasperDesign jdesign = JRXmlLoader.load(StringValue.DeNghiThanhToanNo);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
            
            //Insert Reminder
            InsertReminder();
            
              //Refesh Page
            App.setRoot(StringValue.Recover);

        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void InsertReminder(){
        Integer Count=DBConnection.getInstance().CountReminder()+1;
        String MaPhieu = "NNN"+String.valueOf(Count);
        Integer rBatch = Integer.parseInt(Batch);
        String LoaiHD="Doi No";
        DBConnection.getInstance().InsertReminder(MaPhieu, ValueCheck.IDNV, rBatch, MaHD, LoaiHD);
    }
    
    public void btnBack() {
        Stage thisStage = (Stage) lbDate.getScene().getWindow();
        thisStage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
