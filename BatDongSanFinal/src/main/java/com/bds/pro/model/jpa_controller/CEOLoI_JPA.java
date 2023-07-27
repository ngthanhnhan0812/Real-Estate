/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model.jpa_controller;

import com.bds.pro.model.ListofCus;
import com.bds.pro.model.ListofInv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ntnha
 */
public class CEOLoI_JPA {

    private static final String dbURL = "jdbc:sqlserver://115.73.212.222:8888;databaseName=sem2_ngan;encrypt=true;trustServerCertificate=true;";
    private static final String username = "ngan";
    private static final String password = "123";
    Connection conn;
    private static final String GET_INV = "select cdt.id,cdt.fullname,cdt.sdt,cdt.email,da.tenduan,k.tenkhu,sp.tensp,sp.dongia "
            + "from ChuDauTu cdt "
            + "join DuAn da on da.idchudautu = cdt.id "
            + "join Khu k on k.idduan = da.id "
            + "join SanPham sp on sp.idkhu = k.id";

    public CEOLoI_JPA() {
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ListofInv> getInv() {
        ObservableList<ListofInv> ol = FXCollections.observableArrayList();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(GET_INV);
            while (rs.next()) {
                ListofInv item = new ListofInv();
                //select cdt.id,cdt.fullname,cdt.sdt,cdt.email,da.tenduan,k.tenkhu,sp.tensp,sp.dongia
                item.setId(rs.getInt("id"));
                item.setFullname(rs.getString("fullname"));
                item.setSdt(rs.getString("sdt"));
                item.setEmail(rs.getString("email"));
                item.setTenduan(rs.getString("tenduan"));
                item.setTenkhu(rs.getString("tenkhu"));
                item.setTensp(rs.getString("tensp"));
                item.setDongia(rs.getString("dongia"));
                ol.add(item);
            }
            sm.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("ERROR getInv");
            System.out.println(e.getMessage());
        }
        return ol;
    }
}
