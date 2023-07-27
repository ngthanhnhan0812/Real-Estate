/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import com.bds.pro.App;
import com.bds.pro.res.StringValue;
import java.io.IOException;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ADMIN
 */
public class MyDialog extends Stage{
     public MyDialog(Stage owner) {
            super();
            initOwner(owner);
            setTitle("Login App");
            Group root = new Group();
            Scene scene = new Scene(root, 600, 400);
            setScene(scene);
            initModality(Modality.APPLICATION_MODAL);
            initStyle(StageStyle.UNDECORATED);
            Parent p;
            try {
                p = App.loadFXML(StringValue.LoginForm);
                scene.setRoot(p);
            } catch (IOException ex) {
                System.out.println("com.demo.test.App.MyDialog.<init>()");
            }

        }
}
