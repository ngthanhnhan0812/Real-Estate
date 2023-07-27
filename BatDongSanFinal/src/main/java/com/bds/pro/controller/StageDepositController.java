/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import com.bds.pro.App;
import com.bds.pro.model.DBConnection;
import com.bds.pro.model.Deposit;
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
public class StageDepositController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lbDate, lbMonth, lbYear, lbFullName, lbAddress, lbReason, lbDeposit;

    @FXML
    Label lbError;

    @FXML
    MFXTextField tfDepositW;

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
        String Reason = "Thu Tiền Đặt Cọc Của ";
        ObservableList<Deposit> ls = DBConnection.getInstance().HDDC(ValueCheck.HDDC);
        lbFullName.setText(ls.get(0).getNameClient());
        lbAddress.setText(ls.get(0).getAddress());
        lbReason.setText(Reason + ls.get(0).getNameClient());
        lbDeposit.setText(ls.get(0).getDeposits() + " VNĐ");
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
            DBConnection.getInstance().changeStatusHDDC();

            //INSERT INTO Phieu_thu
            InsertReceipt();
            App.setRoot(StringValue.DepositContract);

        }
    }

    private void InsertReceipt() {
        String MaHD = ValueCheck.HDDC;
        Integer Dot = 0;
        Long Sotien = formatCurrency(lbDeposit.getText());
        Integer IDNV = ValueCheck.IDNV;
        String LyDo = lbReason.getText();
        String LoaiHD = "HDDC";
        DBConnection.getInstance().InsertReceipt(MaHD, Dot, Sotien, IDNV, LyDo, LoaiHD);
    }

    public static Long formatCurrency(String str) {
        Long finalValue = null;
        try {
            String fristReplace = str.replaceAll(",", "");
            String secondReplace = fristReplace.replace("VNĐ", "");
            String thirdReplace = secondReplace.replaceAll("\\s+","");
            finalValue = Long.parseLong(thirdReplace);
        } catch (NumberFormatException e) {
            System.out.println("ERROR formatCurrency");
            System.out.println(e.getMessage());
        }
        return finalValue;
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
