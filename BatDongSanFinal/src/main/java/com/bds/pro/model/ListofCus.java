/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ntnha
 */
public class ListofCus {

    //kh.id,kh.fullname,kh.phone,kh.email,sp.tensp,k.tenkhu,mb.thoigian,sp.dongia 
    private int id;

    private String fullname;

    private String phone;

    private String email;

    private String tenkhu;

    private String tensp;

    private String thoigian;

    private String dongia;

    public ListofCus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        String rThoiGian = formatDate(thoigian);
        this.thoigian = rThoiGian;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    private String formatDate(String Date) {
        String dayFormat = "";
        try {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dt.parse(Date);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
            dayFormat = String.valueOf(dt1.format(date));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return dayFormat;
    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (idsp != null ? id.hashCode() : 0);
//        return hash;
//    }
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof KhMuaBan)) {
//            return false;
//        }
//        KhMuaBan other = (KhMuaBan) object;
//        if ((this.idsp == null && other.idsp != null) || (this.idsp != null && !this.idsp.equals(other.idsp))) {
//            return false;
//        }
//        return true;
//    }
    @Override
    public String toString() {
        return "com.demo.coffee.model.ListofCus[ id=" + id + " ]";
    }
}
