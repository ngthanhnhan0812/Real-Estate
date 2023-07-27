module com.bds.pro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;
    requires MaterialFX;
    requires java.base;
    requires org.json;
    requires gson;
    requires itext;
    requires org.apache.commons.io;     
    requires com.jfoenix;

    opens com.bds.pro.controller to javafx.fxml;
    opens com.bds.pro.model to gson;
            
    opens com.bds.pro to javafx.fxml;
    exports com.bds.pro;
}
