/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.Batch;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.Sale;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
public class StageSaleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lbDate, lbMonth, lbYear, lbFullName, lbAddress, lbReason, lbDeposit;

    @FXML
    Label lbError;

    @FXML
    MFXTextField tfDepositW;
    
    Integer Dot=0;
    ObservableList<Batch> lsJson = FXCollections.observableArrayList();
    
    String jsString;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setDateTime();
        getInformation();
    }   
    
    private void setDateTime() {
        LocalDate currentdate = LocalDate.now();
        lbDate.setText(String.valueOf(currentdate.getDayOfMonth()));
        lbMonth.setText(String.valueOf(currentdate.getMonthValue()));
        lbYear.setText(String.valueOf(currentdate.getYear()));
    }
    
    private void getInformation() {
        ObservableList<Sale> ls = DBConnection.getInstance().HDMB(ValueCheck.HDMB);
        lbFullName.setText(ls.get(0).getNameClient());
        lbAddress.setText(ls.get(0).getAddress());
        makeListbyJson(ls.get(0).getDotThanhToan());
        jsString=ls.get(0).getDotThanhToan();
        for (int i = 0; i < lsJson.size(); i++) {
            if ("0".equals(lsJson.get(i).getStatus())) {
                lbReason.setText("Thu Tiền Đợt "+lsJson.get(i).getID());
                lbDeposit.setText(lsJson.get(i).getMoney());
                Dot=Integer.parseInt(lsJson.get(i).getID());
                break;
            }
        }  
    }
    
    private void makeListbyJson(String jsonString) {
        try {
            JSONArray jsonarray = new JSONArray(jsonString);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Batch bt = new Batch();
                bt.setID(jsonobject.getString("ID"));
                bt.setMoney(jsonobject.getString("money"));
                bt.setDate(jsonobject.getString("date"));
                bt.setStatus(jsonobject.getString("status"));
                lsJson.add(bt);
            }
        } catch (JSONException e) {
            System.out.println("ERROR makeListbyJson");
            System.out.println(e.getMessage());
        }
    }
    
    public void btnPrint() throws JRException, IOException {
        if (tfDepositW.getText().isEmpty()) {
            lbError.setText("Vui Lòng Nhập Trường Này");
        } else {
            lbError.setText("");
            HashMap hm = new HashMap();
            hm.put("Day", lbDate.getText());
            hm.put("Month", lbMonth.getText());
            hm.put("Year", lbYear.getText());
            hm.put("FullName", lbFullName.getText());
            hm.put("Address", lbAddress.getText());
            hm.put("Reason", lbReason.getText());
            hm.put("Deposits", lbDeposit.getText());
            hm.put("DepositsW", tfDepositW.getText());
            //Close recepitStage
            Stage thisStage = (Stage) tfDepositW.getScene().getWindow();
            thisStage.close();
            showAlert(Alert.AlertType.INFORMATION, "Print Receipt", "Success Print Receipt");
            //Jasper Report
            JasperDesign jdesign = JRXmlLoader.load(StringValue.PhieuThu);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);

            //Update Status
            changeStatusJson();

            //INSERT INTO Phieu_thu
            InsertReceipt();
            App.setRoot(StringValue.SaleContract);

        }
    }
    
     private void InsertReceipt() {
        String MaHD = ValueCheck.HDMB;     
        Integer IDNV = ValueCheck.IDNV;
        Long SoTien=Long.parseLong(lbDeposit.getText());
        String LyDo = lbReason.getText();
        String LoaiHD = "HDMB";
        DBConnection.getInstance().InsertReceipt(MaHD, Dot, SoTien, IDNV, LyDo, LoaiHD);
    }
     
    private void changeStatusJson(){      
        JSONArray jsonarray = new JSONArray(jsString);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            if (String.valueOf(Dot).equals(jsonobject.getString("ID"))) {
                jsonobject.put("status", "1");
                jsonarray.put(i,jsonobject);
            }          
        }
        String newJsonStr = String.valueOf(jsonarray);
        DBConnection.getInstance().changeJsonStatus(newJsonStr);
    }
    
    public void btnBack() {
        Stage thisStage = (Stage) tfDepositW.getScene().getWindow();
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
