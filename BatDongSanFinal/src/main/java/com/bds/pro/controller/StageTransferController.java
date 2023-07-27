/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.Transfer;
import com.bds.pro.model.ValueCheck;
import com.bds.pro.res.StringValue;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
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
public class StageTransferController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lbDate, lbMonth, lbYear, lbFullName, lbReason, lbAddress;

    @FXML
    Label lbTransfer, lbTransferW;

    @FXML
    MFXTextField tfTransferW, tfTransfer;

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
        String Reason = "Thu Tiền Làm Hợp Đồng Chuyển Nhượng ";
        ObservableList<Transfer> ls = DBConnection.getInstance().HDCN(ValueCheck.HDCN);
        lbFullName.setText(ls.get(0).getNameClient());
        lbAddress.setText(ls.get(0).getAddress());
        lbReason.setText(Reason);

    }

    public void btnPrint() throws JRException, IOException {
        Boolean fristCheck = checkTransfer();
        Boolean secondCheck = checkTransferW();
        if (fristCheck == false && secondCheck == false) {
            lbTransfer.setText("");
            lbTransferW.setText("");
            HashMap hm = new HashMap();
            hm.put("Day", lbDate.getText());
            hm.put("Month", lbMonth.getText());
            hm.put("Year", lbYear.getText());
            hm.put("FullName", lbFullName.getText());
            hm.put("Address", lbAddress.getText());
            hm.put("Reason", lbReason.getText());
            hm.put("Deposits", tfTransfer.getText());
            hm.put("DepositsW", tfTransferW.getText());
            //Close ReceiptStage
            Stage thisStage = (Stage) tfTransferW.getScene().getWindow();
            thisStage.close();
            showAlert(Alert.AlertType.INFORMATION, "Print Receipt", "Success Print Receipt");
            
            //Jasper Report
            JasperDesign jdesign = JRXmlLoader.load(StringValue.PhieuThu);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jprint, false);
            
            //Update Status
            DBConnection.getInstance().changeStatusHDCN();
            
            //INSERT INTO Phieu_thu
            InsertReceipt();
            App.setRoot(StringValue.TransferContract);
        } else {
            showAlert(Alert.AlertType.ERROR, "ERROR", "Please Check Back");
        }
    }

    private boolean checkTransfer() {
        Boolean Check = false;
        if (tfTransfer.getText().isEmpty()) {
            lbTransfer.setText("");
            lbTransfer.setText("Please Input This Field");
            Check = true;
        } else if (!tfTransfer.getText().matches("\\d+")) {
            lbTransfer.setText("");
            lbTransfer.setText("This Field Only Contains Number");
            Check = true;
        } else {
            lbTransfer.setText("");          
        }
        return Check;
    }

    private boolean checkTransferW() {
        Boolean Check = false;
        if (tfTransferW.getText().isEmpty()) {
            lbTransferW.setText("");
            lbTransferW.setText("Please Input This Field");
            Check = true;
        } else {
            lbTransferW.setText("");
        }
        return Check;
    }

    public void btnBack() {
        Stage thisStage = (Stage) tfTransferW.getScene().getWindow();
        thisStage.close();
    }
    
    private void InsertReceipt() {
        String MaHD = ValueCheck.HDCN;
        Integer Dot = 0;
        Long Sotien = Long.parseLong(tfTransfer.getText());
        Integer IDNV = ValueCheck.IDNV;
        String LyDo = lbReason.getText();
        String LoaiHD = "HDCN";
        DBConnection.getInstance().InsertReceipt(MaHD, Dot, Sotien, IDNV, LyDo, LoaiHD);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
