/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.Reminder;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class FristDebtController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lbName, lbMaHD, lbDate, lbDay, lbMonth, lbYear;

    String MaHD;
    String Batch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getContract();
        getInformation();
    }

    private void getContract() {
        String[] parts = ValueCheck.FristDebt.split("-");
        MaHD = parts[0];
        Batch = parts[1];
    }

    private void getInformation() {
        try {
            ObservableList<Reminder> ls = DBConnection.getInstance().getInfoReminder(MaHD);
            lbName.setText(ls.get(0).getNameClient());
            lbMaHD.setText(ls.get(0).getMaHD());
            lbDate.setText(ls.get(0).getDate());
            setDate(ls.get(0).getDate());
        } catch (Exception e) {
            System.out.println("ERROR getInformation");
            System.out.println(e.getMessage());
        }
    }

    private void setDate(String Date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(Date);
            Calendar calSet = Calendar.getInstance();
            calSet.setTime(d);
            lbDay.setText(String.valueOf(calSet.get(Calendar.DATE)));
            lbMonth.setText(String.valueOf(calSet.get(Calendar.MONTH) + 1));
            lbYear.setText(String.valueOf(calSet.get(Calendar.YEAR)));
        } catch (ParseException e) {
        }
    }

    public void btnPrint() throws IOException {
        try {
            HashMap hm = new HashMap();
            hm.put("Name", lbName.getText());
            hm.put("MaHD", lbMaHD.getText());
            hm.put("Date", lbDate.getText());
            hm.put("Thang", lbMonth.getText());
            hm.put("Ngay", lbDay.getText());
            hm.put("Nam", lbYear.getText());
            //Close Stage
            Stage thisStage = (Stage) lbDate.getScene().getWindow();
            thisStage.close();
            showAlert(Alert.AlertType.INFORMATION, "Print Reminder", "Success Print Reminder");

            //Jasper
            JasperDesign jdesign = JRXmlLoader.load(StringValue.PhieuNhacNo);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
            
            //Insert
            InsertReminder();
            
            //Refesh Page
            App.setRoot(StringValue.Announce);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void InsertReminder(){
        Integer Count=DBConnection.getInstance().CountReminder()+1;
        String MaPhieu = "NNN"+String.valueOf(Count);
        Integer rBatch = Integer.parseInt(Batch);
        String LoaiHD="Nhac No";
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
