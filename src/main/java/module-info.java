module edu.ijse.inspira1stsemesterproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.desktop;
    requires com.jfoenix;
    //requires java.mail;
    requires net.sf.jasperreports.core;
    requires javax.mail.api;


    opens edu.ijse.inspira1stsemesterproject.controller to javafx.fxml;
    opens edu.ijse.inspira1stsemesterproject.dto.tm to javafx.base;
    exports edu.ijse.inspira1stsemesterproject;
}