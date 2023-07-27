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
public class PhuLucHopDong {

    private String mahd;
//    @Column(name = "kingay")
//    @Temporal(TemporalType.DATE)
    private LocalDate kingay;
//    @Column(name = "khoan")
    private Integer khoan;
//    @Column(name = "dieu")
    private Integer dieu;
//    @Column(name = "dieukhoancu")
    private String dieukhoancu;
//    @Column(name = "dieukhoanmoi")
    private String dieukhoanmoi;
//    @JoinColumn(name = "hd_mua_ban", referencedColumnName = "mahd")
//    @ManyToOne
    private String hdMuaBan;

    public PhuLucHopDong() {
    }

    public PhuLucHopDong(String mahd) {
        this.mahd = mahd;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public LocalDate getKingay() {
        return kingay;
    }

    public void setKingay(LocalDate kingay) {
        this.kingay = kingay;
    }

    public Integer getKhoan() {
        return khoan;
    }

    public void setKhoan(Integer khoan) {
        this.khoan = khoan;
    }

    public Integer getDieu() {
        return dieu;
    }

    public void setDieu(Integer dieu) {
        this.dieu = dieu;
    }

    public String getDieukhoancu() {
        return dieukhoancu;
    }

    public void setDieukhoancu(String dieukhoancu) {
        this.dieukhoancu = dieukhoancu;
    }

    public String getDieukhoanmoi() {
        return dieukhoanmoi;
    }

    public void setDieukhoanmoi(String dieukhoanmoi) {
        this.dieukhoanmoi = dieukhoanmoi;
    }

    public String getHdMuaBan() {
        return hdMuaBan;
    }

    public void setHdMuaBan(String hdMuaBan) {
        this.hdMuaBan = hdMuaBan;
    }

    @Override
    public String toString() {
        return this.getMahd();
    }
}
