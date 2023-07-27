/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bds.pro.model;

//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author thuan
 */
public class FileHandling {
    AlertHandling alert = new AlertHandling();
    public boolean checkValues(File file) {
        try {
            if (file == null) {
                alert.showAlert(Alert.AlertType.ERROR, "ERROR", "Please fill up requirement");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Not Have File");
        }
        return true;
    }

    public File TextFileChooser(Stage stage) {
        FileChooser fc = new FileChooser();
        File file;

        fc.setTitle("Chọn Văn Bản");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.doc", "*.pdf");
        fc.getExtensionFilters().add(ext);
        file = fc.showOpenDialog(stage);
        return file;
    }

    public File ImageFileChooser(Stage stage) {
        FileChooser fc = new FileChooser();
        File file;

        fc.setTitle("Chọn Hình Ảnh");
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Lọc theo", "*.jpg", "*.png");
        fc.getExtensionFilters().add(ext);
        file = fc.showOpenDialog(stage);
        return file;
    }

    public boolean saveFile(Node node) {
        Path pathProject = Path.of(System.getProperty("user.dir"));
        String path = pathProject.toString() + "\\target\\Save.pdf";
        Path pathFile = Path.of(path);
        File file = new File(path);

        try {
            try {
                Document Doc = new Document();
                PdfWriter.getInstance(Doc, new FileOutputStream(path));
                Doc.open();
                Doc.add(new Paragraph("abc"));
                Doc.close();
            } catch (Exception ex) {
            }

        } catch (Exception e) {
        }
        return true;
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
            String projectPath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\uploads\\";
            String PathSave = userDirectory + projectPath;
            File outputfile = new File(PathSave + fileName);
//            ImageIO.write(bi, "png", outputfile);
        }
    }

    public void ReadImage(ImageView imgView, String name) throws IOException {
        try {
            Path pathProject = Path.of(System.getProperty("user.dir"));
            String projectPath = "\\src\\main\\resources\\com\\bds\\pro\\asset\\uploads\\";
            File myObj = new File(pathProject.toString()+projectPath+name);
            BufferedImage image = ImageIO.read(myObj);
            if(image != null) {
                imgView.setImage(new Image(myObj.toURI().toString(), 200, 150, false, true));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
