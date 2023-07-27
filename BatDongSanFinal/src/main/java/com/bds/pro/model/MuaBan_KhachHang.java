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
public class MuaBan_KhachHang {
    private String fullname;
    private String hokhau;
    private LocalDate ngay;
    private LocalDate ngaysinh;
    private String socmnd;
    private LocalDate ngaycap;
    private String noicap;
    private String mahd;
    private String dot_tt;

    public MuaBan_KhachHang() {
        super();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getHokhau() {
        return hokhau;
    }

    public void setHokhau(String hokhau) {
        this.hokhau = hokhau;
    }
    
    

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
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

    public String getNoicap() {
        return noicap;
    }

    public void setNoicap(String noicap) {
        this.noicap = noicap;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getDot_tt() {
        return dot_tt;
    }

    public void setDot_tt(String dot_tt) {
        this.dot_tt = dot_tt;
    }
    
    @Override
    public String toString(){
        return this.getMahd();
    }
}
