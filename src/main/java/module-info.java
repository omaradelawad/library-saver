module com.librarysaver.librarysaver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;



    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fluentui;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.testng;
    requires java.naming;
    requires eu.iamgio.animated;
    requires java.desktop;
    requires atlantafx.base;
    requires AnimateFX;
    requires fxpopup;
    // opens com.librarysaver.librarysaver.entities to org.hibernate.orm.core;
    opens com.librarysaver.librarysaver.entities to org.hibernate.orm.core, javafx.base, javafx.fxml;
    opens com.librarysaver.librarysaver to javafx.fxml;
    exports com.librarysaver.librarysaver;
}