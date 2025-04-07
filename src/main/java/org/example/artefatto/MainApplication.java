package org.example.artefatto;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.artefatto.DAO.ICategoriaImpl;
import org.example.artefatto.DAO.IMainAppImpl;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setTitle("ArteFatto");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        // 🎬 Animación de Fade In
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), scene.getRoot());
        fadeIn.setFromValue(0); // Opacidad inicial (invisible)
        fadeIn.setToValue(1);   // Opacidad final (visible)
        fadeIn.play();

        stage.show();
    }

    public static void crearTablas() {
        // Abrir una sesión de Hibernate
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
    }

    public static void main(String[] args) {

        Font font = Font.loadFont(MainApplication.class.getResourceAsStream("/resources/fonts/Shrikhand-Regular.ttf"), 20);
        crearTablas();
        IMainAppImpl mainApp = new IMainAppImpl();
        IUsuarioImpl iUsu = new IUsuarioImpl();
        mainApp.generarCategoriasBase();

        Usuario U = iUsu.comprobarUsuario("a", "a");
        ICategoriaImpl iCat = new ICategoriaImpl();

        // BARAJAS -----------------------------------------------------------------------------------------------------
        mainApp.anadirProducto("Baraja001", U, iCat.getCategoriesFromDatabase().get(1), 20.0, true, "/resources/img/Barajas/Baraja001.jpg");

        // DIY -----------------------------------------------------------------------------------------------------

        // ILUSTRACIONES -----------------------------------------------------------------------------------------------------

        // POCIONES -----------------------------------------------------------------------------------------------------

        launch();
    }
}

