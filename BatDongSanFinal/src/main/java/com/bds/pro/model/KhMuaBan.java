/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ntnha
 */

public class KhMuaBan implements Serializable {

    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "int")
    private Integer int1;
//    @Column(name = "thoigian")
//    @Temporal(TemporalType.DATE)
    private Date thoigian;
//    @Column(name = "trangthai")
    private String trangthai;
//    @JoinColumn(name = "idkh", referencedColumnName = "id")
//    @ManyToOne
    private KhachHang idkh;
//    @JoinColumn(name = "idsp", referencedColumnName = "id")
//    @ManyToOne
    private SanPham idsp;

    public KhMuaBan() {
    }

    public KhMuaBan(Integer int1) {
        this.int1 = int1;
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public Date getThoigian() {
        return thoigian;
    }

    public void setThoigian(Date thoigian) {
        this.thoigian = thoigian;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public KhachHang getIdkh() {
        return idkh;
    }

    public void setIdkh(KhachHang idkh) {
        this.idkh = idkh;
    }

    public SanPham getIdsp() {
        return idsp;
    }

    public void setIdsp(SanPham idsp) {
        this.idsp = idsp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int1 != null ? int1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KhMuaBan)) {
            return false;
        }
        KhMuaBan other = (KhMuaBan) object;
        if ((this.int1 == null && other.int1 != null) || (this.int1 != null && !this.int1.equals(other.int1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.pro.model.KhMuaBan[ int1=" + int1 + " ]";
    }
    
}
