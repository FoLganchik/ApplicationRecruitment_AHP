module com.example.applicationrecruitment {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires com.google.gson;
    requires jlatexmathfx;

    opens com.example.applicationrecruitment to javafx.fxml;
    exports com.example.applicationrecruitment;
    exports com.example.applicationrecruitment.controller;
    opens com.example.applicationrecruitment.controller to javafx.fxml;
}