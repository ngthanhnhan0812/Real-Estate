/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
/**
 *
 * @author ntnha
 */
public class createGrid {
    public static Button createColName(String colName, Integer width, Integer height, String id) {
        //TODO Create Name of Col
        try {
            Button btn = new Button(colName);
            btn.setId(id);
            btn.setMinSize(width, height);
            btn.setStyle(
                    "-fx-border-color: black;"
                    + "-fx-alignment: center;"
                    + "-fx-text-fill: #24b067;"
                    + "-fx-font-size: 16;"
                    + "-fx-border-width: 0.5;"
                    + "-fx-background-color: #a4cab6;"
            );
            actionForCol(btn);
            return btn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Label setDataLabelCol(String Value, Integer width, Integer height) {
        try {
            Label lb = new Label(Value);
            lb.setMinSize(width, height);
            lb.setStyle(
                    "-fx-border-color: black;"
                    + "-fx-alignment: center;"
                    + "-fx-text-fill: black;"
                    + "-fx-border-style: none solid solid solid;"
                    + "-fx-font-size: 14;"
                    + "-fx-border-width: 0.5;"
            );
            return lb;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void actionForCol(Button btn) {
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent Event) {
                System.out.println(".handle()");
            }
        });
    }

    public static VBox vboxButton(Button btn, Integer Width, Integer Height) {
        try {
            VBox vb = new VBox(btn);
            vb.setMinSize(Width, Height);
            vb.setStyle(
                    "-fx-border-color: black;"
                    + "-fx-border-style: none solid solid solid;"
                    + "-fx-border-width: 0.5;"
                    + "-fx-alignment: center;"
            );
            return vb;
        } catch (Exception e) {
            System.out.println("ERROR Create VBox");
            System.out.println(e.getMessage());
        }
        return null;
    }
}

