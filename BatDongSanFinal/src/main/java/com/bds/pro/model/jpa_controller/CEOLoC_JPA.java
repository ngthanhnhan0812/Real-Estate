/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model.jpa_controller;

import com.bds.pro.model.KhMuaBan;
import com.bds.pro.model.ListofCus;
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
public class CEOLoC_JPA {

    private static final String dbURL = "jdbc:sqlserver://115.73.212.222:8888;databaseName=sem2_ngan;encrypt=true;trustServerCertificate=true;";
    private static final String username = "ngan";
    private static final String password = "123";
    Connection conn;
    private static final String GET_CUS = "select kh.id,kh.fullname,kh.phone,kh.email,sp.tensp,k.tenkhu,mb.thoigian,sp.dongia "
            + "from KhachHang kh "
            + "join KhMuaBan mb on mb.idkh = kh.id "
            + "join SanPham sp on sp.id = mb.idsp "
            + "join Khu k on k.id = sp.idkhu ";
    
    public CEOLoC_JPA() {
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ListofCus> getCus() {
        ObservableList<ListofCus> sp = FXCollections.observableArrayList();
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(GET_CUS);
            while (rs.next()) {
                ListofCus item = new ListofCus();
                item.setId(rs.getInt("id"));
                item.setFullname(rs.getString("fullname"));
                item.setPhone(rs.getString("phone"));
                item.setEmail(rs.getString("email"));
                item.setTensp(rs.getString("tensp"));
                item.setTenkhu(rs.getString("tenkhu"));
                item.setThoigian(rs.getString("thoigian"));
                item.setDongia(rs.getString("dongia"));
                sp.add(item);
            }
            sm.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("ERROR getCus");
            System.out.println(e.getMessage());
        }
        return sp;
    }

}
