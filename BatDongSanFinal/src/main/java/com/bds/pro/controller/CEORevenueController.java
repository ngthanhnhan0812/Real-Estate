/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bds.pro.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author ntnha
 */
public class CEORevenueController implements Initializable {

    @FXML
    private LineChart<String, Number> cData;
    

    @FXML
    private static final String dbURL = "jdbc:sqlserver://115.73.212.222:8888;databaseName=sem2_ngan;encrypt=true;trustServerCertificate=true;";
    private static final String username = "ngan";
    private static final String password = "123";
    Connection conn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            GenerateLC();
        } catch (SQLException ex) {
            Logger.getLogger(CEORevenueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void GenerateLC() throws SQLException {
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        conn = DriverManager.getConnection(dbURL, username, password);
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("select format(thoigian,'MM-yyyy') as Date, SUM(sotien) as Total from Phieu_thu group by format(thoigian,'MM-yyyy') order by Date asc;");
            while (rs.next()) {
                String strMonth = String.valueOf(rs.getString("Date"));
                dataSeries1.getData().add(new XYChart.Data<String, Number>(strMonth, rs.getInt("Total")));
            }
            sm.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("ERROR findall");
            System.out.println(e.getMessage());
        }
        dataSeries1.setName("Doanh thu");
        cData.getData().addAll(dataSeries1);
        cData.setTitle("Doanh thu trong năm");
    }
}
