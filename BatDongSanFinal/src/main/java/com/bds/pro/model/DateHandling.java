/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;

/**
 *
 * @author thuan
 */
public class DateHandling {
    AlertHandling alert = new AlertHandling();
    public boolean checkValues(MFXDatePicker dpk) {
        try {
            if(dpk.getValue().toString().isEmpty()){
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please fill up requirement");
                return false;
            }
        } catch (Exception e) {
            System.out.println("String no ");
        }
        return true;
    }

    public Period dateDifference(String begin, String end) {
        DateTimeFormatter dtfmt = DateTimeFormatter.ofPattern("yy:MM:dd");
        LocalDate.parse(begin).format(dtfmt);
        LocalDate.parse(end).format(dtfmt);
        Period diff = Period.between(LocalDate.parse(begin), LocalDate.parse(end));
        return diff;
    }

    public boolean isExpiry(LocalDate begin, LocalDate end) {
        Period date = dateDifference(begin.toString(), end.toString());
        return !date.isNegative() || date.isZero() ? true : false;
    }

//    public String statusDate(String begin, String end) {
//        LocalDate now = LocalDate.now();
//        if (isExpiry(now.toString(), begin) == false) {
//            return "Comming Soon";
//        }
//        if (isExpiry(now.toString(), begin) && isExpiry(now.toString(), end) == false) {
//            return "Active";
//        } else {
//            return "Expiry";
//        }
//    }

    public LocalDate dateFomatter(String date) {
        DateTimeFormatter dtfmt = DateTimeFormatter.ofPattern("yy:MM:dd");
        LocalDate reDate = LocalDate.parse(date);
        reDate.format(dtfmt);
        return reDate;
    }

    public LocalDate dateFomatterX(String date) {
        DateTimeFormatter dtfmt = DateTimeFormatter.ofPattern("dd:MM:yyyy");
        LocalDate reDate = LocalDate.parse(date);
        reDate = LocalDate.parse(reDate.format(dtfmt));
        return reDate;
    }
}
