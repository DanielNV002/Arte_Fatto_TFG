module org.example.artefatto {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires javafx.graphics;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires java.desktop;

    opens org.example.artefatto to javafx.fxml;
    exports org.example.artefatto;
    exports org.example.artefatto.Controladores;
    opens org.example.artefatto.Controladores to javafx.fxml;
}