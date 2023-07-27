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

public class ChuDauTu implements Serializable {

    
    private String id;
    
    private String fullname;
    
    private String logo;
    
    private String sdt;
    
    private String email;
    
    private String diachi;
    
    private String descrip;
    
    private Integer duansapmo;
   
    private Integer duandangmo;
    
    private Integer duanbangiao;
    
    private String trangthai;
    
   

    public ChuDauTu() {
    }

    public ChuDauTu(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Integer getDuansapmo() {
        return duansapmo;
    }

    public void setDuansapmo(Integer duansapmo) {
        this.duansapmo = duansapmo;
    }

    public Integer getDuandangmo() {
        return duandangmo;
    }

    public void setDuandangmo(Integer duandangmo) {
        this.duandangmo = duandangmo;
    }

    public Integer getDuanbangiao() {
        return duanbangiao;
    }

    public void setDuanbangiao(Integer duanbangiao) {
        this.duanbangiao = duanbangiao;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
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
        if (!(object instanceof ChuDauTu)) {
            return false;
        }
        ChuDauTu other = (ChuDauTu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getFullname();
    }
}
