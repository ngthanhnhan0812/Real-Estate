/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author ADMIN
 */
public class GridTable {

    private static final String styleBorder = "-fx-border-color: black; -fx-border-width: 0.5; -fx-border-style: none solid solid solid;";
    private static final String styleAlight = "-fx-alignment: center;";
    private static final String styleColor = "-fx-background-color: #a4cab6;-fx-text-fill: #0d5918;";
    private static final String styleFont = "-fx-font-size: 13;";

    public VBox cellHeader(Button btn, Integer Height, Integer Width) {
        try {
            VBox vb = new VBox();
            btn.setStyle(
                    styleBorder + styleAlight + styleColor + styleFont
            );
            btn.setMinSize(Width, Height);
            vb.getChildren().add(btn);
            return vb;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR cellHeader");
        }
        return null;
    }
    
    public VBox cellHeaderNA(String colName, Integer Height, Integer Width) {
        try {
            VBox vb = new VBox();
            Button btn = new Button(colName);
            btn.setStyle(
                    styleBorder + styleAlight + styleColor + styleFont
            );
            btn.setMinSize(Width, Height);
            vb.getChildren().add(btn);
            return vb;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR cellHeaderNA");
        }
        return null;
    }

    public VBox cellData(String Text, Integer Height, Integer Width) {
        try {
            VBox vb = new VBox();
            Label lb = new Label(Text);
            vb.setStyle(
                    styleBorder + styleAlight + styleFont
            );
            vb.setMinSize(Width, Height);
            vb.getChildren().add(lb);
            return vb;
        } catch (Exception e) {
            System.out.println("ERROR cellData");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public VBox cellCheckBox(CheckBox ck, Integer Height, Integer Width) {
        try {
            VBox vb = new VBox();
            vb.setStyle(
                    styleBorder + styleAlight + styleFont
            );
            vb.setMinSize(Width, Height);
            vb.getChildren().add(ck);
            return vb;
        } catch (Exception e) {
            System.out.println("ERROR cellDataCheckBox");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public VBox cellButton(Button btn, Integer Height, Integer Width) {
        VBox vb = new VBox();
        try {
            vb.setStyle(
                    styleBorder + styleAlight + styleFont
            );
            vb.setMinSize(Width, Height);
            vb.getChildren().add(btn);
        } catch (Exception e) {
            System.out.println("ERROR cellButton");
            System.out.println(e.getMessage());
        }
        return vb;
    }
    
    public VBox cellGridPane(GridPane gp, Integer Height, Integer Width){
         VBox vb = new VBox();
        try {
            vb.setStyle(
                    styleBorder + styleAlight + styleFont
            );
            vb.setMinSize(Width, Height);
            vb.getChildren().add(gp);
        } catch (Exception e) {
            System.out.println("ERROR cellButton");
            System.out.println(e.getMessage());
        }
        return vb;
    }
   

}
