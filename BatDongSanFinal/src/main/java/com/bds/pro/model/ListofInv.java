/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

/**
 *
 * @author ntnha
 */
public class ListofInv {

    //cdt.id,cdt.fullname,cdt.sdt,cdt.email,da.tenduan,k.tenkhu,sp.tensp,sp.dongia
    private int id;

    private String fullname;

    private String sdt;

    private String email;

    private String tenduan;

    private String tenkhu;

    private String tensp;

    private String dongia;

    public ListofInv() {
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenduan() {
        return tenduan;
    }

    public void setTenduan(String tenduan) {
        this.tenduan = tenduan;
    }

    public String getTenkhu() {
        return tenkhu;
    }

    public void setTenkhu(String tenkhu) {
        this.tenkhu = tenkhu;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    @Override
    public String toString() {
        return "com.demo.coffee.model.ListofInv[ id=" + id + " ]";
    }
}
