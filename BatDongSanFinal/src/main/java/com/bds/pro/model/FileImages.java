/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author PC
 */
public class FileImages {

    public void ReadImage(ImageView imgView, String name) throws IOException {
        try {
            Path pathProject = Path.of(System.getProperty("user.dir"));
            String projectPath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\Images\\";
            File myObj = new File(pathProject.toString() + projectPath + name);
            BufferedImage image = ImageIO.read(myObj);
            if (image != null) {
                imgView.setImage(new Image(myObj.toURI().toString(), 150, 150, false, true));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void ActionWithFile(Stage stage) throws IOException {
        //Get Current Stage: Declare 1 fx:id on ramdom item in current anchorpane
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Chọn Hình Ảnh");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.jpg", "*.png");
        chooser.getExtensionFilters().add(ext);
        File fileChooser = chooser.showOpenDialog(stage);

        if (fileChooser == null) {
            //ALERT ERROR INHERE
        } else {
            //Get File Name: GET and SAVE THIS NAME INTO DATABASE
            String fileName = fileChooser.getName();
            BufferedImage bi = ImageIO.read(fileChooser);
            String userDirectory = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .toString();
            //Create Folder Uploads in folder ASSET
            String projectPath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\Images\\";
            String PathSave = userDirectory + projectPath;
            File outputfile = new File(PathSave + fileName);
            ImageIO.write(bi, "png", outputfile);

        }
    }

    public void writeImage(File file) throws IOException {
        BufferedImage bi = ImageIO.read(file);
        String userDirectory = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString();
        //Create Folder Uploads in folder ASSET
        String projectPath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\Images\\";
        String PathSave = userDirectory + projectPath;
        File outputfile = new File(PathSave + file.getName());
        ImageIO.write(bi, "png", outputfile);
    }

}
