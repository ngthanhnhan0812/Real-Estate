/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.text.DecimalFormat;

/**
 *
 * @author ADMIN
 */
public class Deposit {

    private String MaHD;
    private String NameClient;
    private String NameProduct;
    private String Address;
    private String Deposits;
    private Byte Status; 

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getNameClient() {
        return NameClient;
    }

    public void setNameClient(String NameClient) {
        this.NameClient = NameClient;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String NameProduct) {
        this.NameProduct = NameProduct;
    }

    public String getDeposits() {
        return Deposits;
    }

    public void setDeposits(String Deposits) {
        String rDeposits = formatCurrency(Deposits);
        this.Deposits = rDeposits;
    }
    
    public static String formatCurrency(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Double.parseDouble(amount));
    }

    public Byte getStatus() {
        return Status;
    }

    public void setStatus(Byte Status) {
        this.Status = Status;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
    
    
    
}
