/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thuan
 */
public class ExistData {
    private static ExistData instance = null;
    private final String GetHDChuyenNhuong_ID = "SELECT * FROM HopDongChuyenNhuong WHERE mahd = ?";
    private final String GetNhanVien_ID = "SELECT * FROM NhanVien WHERE id = ?";
    
    private ExistData () {}
    
    public static ExistData getInstance() {
        if (instance == null) {
            instance = new ExistData();
        }
        return instance;
    }
    
    public boolean hasHDChuyenNhuong (String id) {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preS = conn.prepareStatement(GetHDChuyenNhuong_ID);
            preS.setString(1, id);
            ResultSet rs = preS.executeQuery();
            if(!rs.isBeforeFirst()){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
    
    public boolean hasNhanVien (Integer id) {
        Connection conn = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preS = conn.prepareStatement(GetNhanVien_ID);
            preS.setInt(1, id);
            ResultSet rs = preS.executeQuery();
            if(!rs.isBeforeFirst()){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
}
