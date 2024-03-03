module pi.blog.blog {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires java.sql;

    opens pi.blog to javafx.fxml;
    exports pi.blog;
    requires itextpdf;
    requires java.desktop;
    requires jakarta.mail;

    opens pi.blog .models to javafx.base;
    opens pi.blog .controllers  to javafx.fxml;
}