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
public class HopDongDatCoc {
    String mahd;
    int idkh;
    int idsp;
    int sotien;
    String thoihan;
    LocalDate batdau;
    String camkettai;
    String thanhtoan;
    String phongcongchung;
    boolean status;
    LocalDate ngaylap;
    int idnv;

    public HopDongDatCoc() {
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public int getIdkh() {
        return idkh;
    }

    public void setIdkh(int idkh) {
        this.idkh = idkh;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getThoihan() {
        return thoihan;
    }

    public void setThoihan(String thoihan) {
        this.thoihan = thoihan;
    }

    public LocalDate getBatdau() {
        return batdau;
    }

    public void setBatdau(LocalDate batdau) {
        this.batdau = batdau;
    }

    public String getCamkettai() {
        return camkettai;
    }

    public void setCamkettai(String camkettai) {
        this.camkettai = camkettai;
    }

    public String getThanhtoan() {
        return thanhtoan;
    }

    public void setThanhtoan(String thanhtoan) {
        this.thanhtoan = thanhtoan;
    }

    public String getPhongcongchung() {
        return phongcongchung;
    }

    public void setPhongcongchung(String phongcongchung) {
        this.phongcongchung = phongcongchung;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(LocalDate ngaylap) {
        this.ngaylap = ngaylap;
    }

    public int getIdnv() {
        return idnv;
    }

    public void setIdnv(int idnv) {
        this.idnv = idnv;
    }
    
}
