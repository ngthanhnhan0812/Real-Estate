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
public class DuAn implements Serializable {

    private String id;
    
    private String tenduan;

    private String gtduan;

    private String matban;

    private String vitri;

    private String dientich;

    private String phaply;

    private String matdo;

    private String tienich;

    private String dvquanly;

    private String trangthai;

    private String idchudautu;

    private String iddvthicong;

    public DuAn() {
    }

    public DuAn(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenduan() {
        return tenduan;
    }

    public void setTenduan(String tenduan) {
        this.tenduan = tenduan;
    }
    
    public String getGtduan() {
        return gtduan;
    }

    public void setGtduan(String gtduan) {
        this.gtduan = gtduan;
    }

    public String getMatban() {
        return matban;
    }

    public void setMatban(String matban) {
        this.matban = matban;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public String getDientich() {
        return dientich;
    }

    public void setDientich(String dientich) {
        this.dientich = dientich;
    }

    public String getPhaply() {
        return phaply;
    }

    public void setPhaply(String phaply) {
        this.phaply = phaply;
    }

    public String getMatdo() {
        return matdo;
    }

    public void setMatdo(String matdo) {
        this.matdo = matdo;
    }

    public String getTienich() {
        return tienich;
    }

    public void setTienich(String tienich) {
        this.tienich = tienich;
    }

    public String getDvquanly() {
        return dvquanly;
    }

    public void setDvquanly(String dvquanly) {
        this.dvquanly = dvquanly;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getTenchudautu() {
        return idchudautu;
    }

    public void setTenchudautu(String idchudautu) {
        this.idchudautu = idchudautu;
    }

    public String getTendvthicong() {
        return iddvthicong;
    }

    public void setTendvthicong(String iddvthicong) {
        this.iddvthicong = iddvthicong;
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
        if (!(object instanceof DuAn)) {
            return false;
        }
        DuAn other = (DuAn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getTenduan();
    }


}
