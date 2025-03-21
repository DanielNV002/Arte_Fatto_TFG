module org.example.artefatto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires java.naming;

    opens org.example.artefatto to javafx.fxml;
    opens org.example.artefatto.Entities to org.hibernate.orm.core;

    exports org.example.artefatto;
    exports org.example.artefatto.Controladores;
    opens org.example.artefatto.Controladores to javafx.fxml;
}
