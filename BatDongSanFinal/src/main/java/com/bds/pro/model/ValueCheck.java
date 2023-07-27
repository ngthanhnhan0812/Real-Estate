/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public class ValueCheck {

    //Staff
    public static Integer IDNV;

    //Contract
    public static String HDDC;
    public static String HDCN;
    public static String HDMB;

    //Debt
    public static String FristDebt;
    public static String SecondDebt;

    public static KhachHang KhachHang = null;
    public static ObservableList<DatCoc_Khachhang> DatCoc_KhachHang = FXCollections.observableArrayList();
    public static ObservableList<HopDongMuaBan> HDMuaBan = FXCollections.observableArrayList();
    public static Khu KhuDuAn = null;
    public static ObservableList<Khu> Khu = null;
    public static SanPham sp = null;
    public static ObservableList<SanPham> SanPham = FXCollections.observableArrayList();
    public static DuAn DuAn = null;
    public static ObservableList<DuAn> XemDuAn = null;
}
