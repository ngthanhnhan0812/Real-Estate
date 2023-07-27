/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.res;

import java.nio.file.FileSystems;

/**
 *
 * @author ADMIN
 */
public class StringValue {

    private static final String view = "view/";

    public static final String LoginForm = view + "LoginForm";
    public static final String KeToanPage = view + "KeToanPage";
    public static final String DepositContract = view + "DepositContract";
    public static final String TransferContract = view + "TransferContract";
    public static final String SaleContract = view + "SaleContract";
    public static final String Announce = view + "Announce";
    public static final String Recover = view + "Recover";
    //Stage
    public static final String ReceiptDeposit = view + "StageDeposit";
    public static final String ReceiptTransfer = view + "StageTransfer";
    public static final String ReceiptSale = view + "StageSale";
    public static final String FristDebt = view + "FristDebt";
    public static final String SecondDebt = view + "SecondDebt";

    //Kinh Doanh
    public static final String MainPage = view + "MainPage";
    public static final String DuAn = view + "DuAn";
    public static final String DVThiCong = view + "DonViThiCong";
    public static final String DatCoc = view + "DatCoc";
    public static final String DatCoc_KhachHang = view + "DatCoc_Khachhang";
    public static final String ChuyenNhuong = view + "ChuyenNhuong";
    public static final String ChuyenNhuong_KhachHang = view + "ChuyenNhuong_KhachHang";
    public static final String KhachHang = view + "KhachHang";
    public static final String ChuDauTu = view + "ChuDauTu";
    public static final String MuaBan = view + "MuaBan";
    public static final String PhuLuc = view + "PhuLuc";
    public static final String SanPham = view + "SanPham";
    public static final String SubSanPham = view + "SubSanPham";
    public static final String ThanhLy = view + "ThanhLy";

    public static String HOME_PAGE = view + "MainPage";
    public static String CHUDAUTU_PAGE = view + "ChuDauTu";
    public static String DONVITHICONG_PAGE = view + "DonViThiCong";
    public static String DUAN_PAGE = view + "DuAn";
    public static String KHACHHANG_PAGE = view + "KhachHang";
    public static String KHUDUAN_PAGE = view + "KhuDuAn";
    public static String SANPHAM_PAGE = view + "SanPham";
    public static String XEMDUAN_PAGE = view + "XemDuAn";

    //CEO
    public static String CEO_MAIN = view + "CEOForm";

    public static String CEO_REVENUE = view + "CEORevenue";

    public static String CEO_MANAGEMENT = view + "CEOmanagement";
    
    public static String CEOLoC = view + "CEOLoC";
    
    public static String CEOLoI = view + "CEOLoI";

    //JasperReport
    private static final String userDirectory = FileSystems.getDefault()
            .getPath("")
            .toAbsolutePath()
            .toString();
    private static final String projectPath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\Jasper\\";
    public static final String PathJasper = userDirectory + projectPath;
    public static final String PhieuThu = PathJasper + "PhieuThu.jrxml";
    public static final String PhieuNhacNo = PathJasper + "ThongBaoNo.jrxml";
    public static final String DeNghiThanhToanNo = PathJasper + "DeNghiThanhToan.jrxml";
    public static final String DatCocJasper = PathJasper + "DatCoc.jrxml";
    public static final String ChuyenNhuongJasper = PathJasper + "ChuyenNhuong.jrxml";
    public static final String MuaBanJasper = PathJasper + "MuaBan.jrxml";
    public static final String PhuLucJasper = PathJasper + "PhuLuc.jrxml";
    public static final String ThanhLyJasper = PathJasper + "ThanhLy.jrxml";

}
