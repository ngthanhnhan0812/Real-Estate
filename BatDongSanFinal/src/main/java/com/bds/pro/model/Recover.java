/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Recover {

    private String nameClient;
    private String Address;
    private String NgayCap;
    private String NoiCap;
    private String CMND;
    private String NgayKi;
    private String DotThanhToan;

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getNgayCap() {
        return NgayCap;
    }

    public void setNgayCap(String NgayCap) {
        String rNgayCap=formatDay(NgayCap);
        this.NgayCap = rNgayCap;
    }

    public String getNoiCap() {
        return NoiCap;
    }

    public void setNoiCap(String NoiCap) {
        this.NoiCap = NoiCap;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getNgayKi() {
        return NgayKi;
    }

    public void setNgayKi(String NgayKi) {
        String rNgayKi = formatDay(NgayKi);
        this.NgayKi = rNgayKi;
    }

    public String getDotThanhToan() {
        return DotThanhToan;
    }

    public void setDotThanhToan(String DotThanhToan) {
        this.DotThanhToan = DotThanhToan;
    }

    private String formatDay(String Date) {
        String dayFormat ="";
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(Date);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
            dayFormat=String.valueOf(dt1.format(date));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return dayFormat;
    }

}
