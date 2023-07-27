/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.io.Serializable;

/**
 *
 * @author PC
 */

public class Khu implements Serializable {

    
    private String id;
    
    private String tenkhu;
    
    private String huong;
    
    private String dientich;
    
    private String trangthai;
    
    private String idduan;

    public Khu() {
    }

    public Khu(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenkhu() {
        return tenkhu;
    }

    public void setTenkhu(String tenkhu) {
        this.tenkhu = tenkhu;
    }

    public String getHuong() {
        return huong;
    }

    public void setHuong(String huong) {
        this.huong = huong;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getIdduan() {
        return idduan;
    }

    public void setIdduan(String idduan) {
        this.idduan = idduan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Khu)) {
            return false;
        }
        Khu other = (Khu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getTenkhu();
    }
    
}
