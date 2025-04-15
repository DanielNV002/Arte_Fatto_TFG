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

        // USUARIOS ----------------------------------------------------------------------------------------------------
        mainApp.anadirUsuario(null, "admin", "admin", "admin", "admin", "admin", "admin", "/img/Users/UserBaseProfile.png", false);

        Usuario U = iUsu.comprobarUsuario("admin", "admin");
        ICategoriaImpl iCat = new ICategoriaImpl();

        // BARAJAS -----------------------------------------------------------------------------------------------------
        mainApp.anadirProducto("Baraja Dorada", U, iCat.getCategoriesFromDatabase().get(1), 20.0, true, "src/main/resources/img/Product/Barajas/Baraja001.jpg");
        mainApp.anadirProducto("Baraja Real", U, iCat.getCategoriesFromDatabase().get(1), 20.0, true, "src/main/resources/img/Product/Barajas/Baraja002.jpg");
        mainApp.anadirProducto("Baraja Mini-Red", U, iCat.getCategoriesFromDatabase().get(1), 25.0, true, "src/main/resources/img/Product/Barajas/Baraja003.jpg");
        mainApp.anadirProducto("Baraja Gatuna", U, iCat.getCategoriesFromDatabase().get(1), 30.0, true, "src/main/resources/img/Product/Barajas/Baraja004.jpg");
        mainApp.anadirProducto("Baraja Mitica", U, iCat.getCategoriesFromDatabase().get(1), 45.0, true, "src/main/resources/img/Product/Barajas/Baraja005.jpg");

        // DIY ---------------------------------------------------------------------------------------------------------
        mainApp.anadirProducto("V8 Wood Motor", U, iCat.getCategoriesFromDatabase().getFirst(), 20.0, true, "src/main/resources/img/Product/DIY/DIY001.jpg");
        mainApp.anadirProducto("Wood Cabinette", U, iCat.getCategoriesFromDatabase().getFirst(), 20.0, true, "src/main/resources/img/Product/DIY/DIY002.jpg");
        mainApp.anadirProducto("Soporte Portatil", U, iCat.getCategoriesFromDatabase().getFirst(), 25.0, true, "src/main/resources/img/Product/DIY/DIY003.jpg");
        mainApp.anadirProducto("Wooden Calendar", U, iCat.getCategoriesFromDatabase().getFirst(), 30.0, true, "src/main/resources/img/Product/DIY/DIY004.jpg");
        mainApp.anadirProducto("Soporte Plantas", U, iCat.getCategoriesFromDatabase().getFirst(), 45.0, true, "src/main/resources/img/Product/DIY/DIY005.jpg");

        // ILUSTRACIONES -----------------------------------------------------------------------------------------------
        mainApp.anadirProducto("Dark Style", U, iCat.getCategoriesFromDatabase().get(2), 20.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu001.jpg");
        mainApp.anadirProducto("Kittys", U, iCat.getCategoriesFromDatabase().get(2), 20.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu002.jpg");
        mainApp.anadirProducto("Floral", U, iCat.getCategoriesFromDatabase().get(2), 25.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu003.jpg");
        mainApp.anadirProducto("Chibi Boy", U, iCat.getCategoriesFromDatabase().get(2), 30.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu004.jpg");
        mainApp.anadirProducto("Afternoon City", U, iCat.getCategoriesFromDatabase().get(2), 45.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu005.jpg");

        // POCIONES ----------------------------------------------------------------------------------------------------
        mainApp.anadirProducto("Hermit Purple", U, iCat.getCategoriesFromDatabase().get(3), 20.0, true, "src/main/resources/img/Product/Pociones/Pocion001.jpg");
        mainApp.anadirProducto("ILove", U, iCat.getCategoriesFromDatabase().get(3), 20.0, true, "src/main/resources/img/Product/Pociones/Pocion002.jpg");
        mainApp.anadirProducto("Midnight", U, iCat.getCategoriesFromDatabase().get(3), 25.0, true, "src/main/resources/img/Product/Pociones/Pocion003.jpg");
        mainApp.anadirProducto("Spider Venom", U, iCat.getCategoriesFromDatabase().get(3), 30.0, true, "src/main/resources/img/Product/Pociones/Pocion004.jpg");
        mainApp.anadirProducto("Hombre Lobo", U, iCat.getCategoriesFromDatabase().get(3), 45.0, true, "src/main/resources/img/Product/Pociones/Pocion005.jpg");

        launch();
    }
}

