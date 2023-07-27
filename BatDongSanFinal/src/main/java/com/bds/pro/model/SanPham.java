/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author PC
 */

public class SanPham implements Serializable {
    private Integer id;
    
    private String tensp;
    
    private String dientich;
    
    private String mota;
    
    private String dongia;
    
    private String vitri;
    
    private String huong;
    
    private String thuadatso;
    
    private String bandoso;
    
    private String diachi;
    
    private String trangthai;
    
    private String idkhu;

    public SanPham() {
    }

    public SanPham(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public String getHuong() {
        return huong;
    }

    public void setHuong(String huong) {
        this.huong = huong;
    }

    public String getThuadatso() {
        return thuadatso;
    }

    public void setThuadatso(String thuadatso) {
        this.thuadatso = thuadatso;
    }

    public String getBandoso() {
        return bandoso;
    }

    public void setBandoso(String bandoso) {
        this.bandoso = bandoso;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getIdkhu() {
        return idkhu;
    }

    public void setIdkhu(String idkhu) {
        this.idkhu = idkhu;
    }

    @Override
    public String toString() {
        return this.getTensp();
    }
    
}
