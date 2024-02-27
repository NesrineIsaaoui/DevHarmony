module edu.esprit.pi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.io;
    requires java.mail;
    requires jbcrypt;

    opens edu.esprit.pi to javafx.fxml;
    opens edu.esprit.pi.controller to javafx.fxml;
    exports edu.esprit.pi;
    exports edu.esprit.pi.controller;
}
