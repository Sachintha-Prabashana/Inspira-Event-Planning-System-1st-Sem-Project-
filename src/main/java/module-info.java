module edu.ijse.inspira1stsemesterproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.desktop;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;


    opens edu.ijse.inspira1stsemesterproject.controller to javafx.fxml;
    opens edu.ijse.inspira1stsemesterproject.dto.tm to javafx.base;
    exports edu.ijse.inspira1stsemesterproject;
}