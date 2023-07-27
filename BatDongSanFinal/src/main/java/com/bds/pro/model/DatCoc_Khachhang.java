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
public class DatCoc_Khachhang {
    String mahd;
    LocalDate ngaylap;
    int id;
    String fullname;
    LocalDate ngaysinh;
    String socmnd;
    LocalDate ngaycap;
    String tai;
    String hokhau;

    public DatCoc_Khachhang() {
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public LocalDate getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(LocalDate ngaylap) {
        this.ngaylap = ngaylap;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSocmnd() {
        return socmnd;
    }

    public void setSocmnd(String socmnd) {
        this.socmnd = socmnd;
    }

    public LocalDate getNgaycap() {
        return ngaycap;
    }

    public void setNgaycap(LocalDate ngaycap) {
        this.ngaycap = ngaycap;
    }

    public String getTai() {
        return tai;
    }

    public void setTai(String tai) {
        this.tai = tai;
    }

    public String getHokhau() {
        return hokhau;
    }

    public void setHokhau(String hokhau) {
        this.hokhau = hokhau;
    }
    
    
}
