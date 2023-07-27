/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.time.LocalDate;

/**
 *
 * @author thuan
 */
public class HopDongMuaBan {
    String mahd;
    LocalDate ngay;
    String dot_tt;
    String tinhtrangphaply;
    String hddc;

    public HopDongMuaBan() {
        super();
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public String getDot_tt() {
        return dot_tt;
    }

    public void setDot_tt(String dot_tt) {
        this.dot_tt = dot_tt;
    }

    public String getTinhtrangphaply() {
        return tinhtrangphaply;
    }

    public void setTinhtrangphaply(String tinhtrangphaply) {
        this.tinhtrangphaply = tinhtrangphaply;
    }

    public String getHddc() {
        return hddc;
    }

    public void setHddc(String hddc) {
        this.hddc = hddc;
    }
    
    @Override
    public String toString() {
        return this.getMahd();
    }
}
