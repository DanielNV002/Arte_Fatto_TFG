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

    public static void cargarBBDD(){

        IMainAppImpl mainApp = new IMainAppImpl();
        IUsuarioImpl iUsu = new IUsuarioImpl();

        // USUARIOS ----------------------------------------------------------------------------------------------------
        mainApp.anadirUsuario(null, "administrador", "administrador", "administrador@example.com", "administrador address", "Administrador", "admin", "/img/Users/UserBaseProfile.png", false);
        mainApp.anadirUsuario(null, "invitado", "invitado", "invitado@example.com", "invitado address", "Invitado", "invitado", "/img/Users/UserBaseProfile.png", false);

        Usuario U = iUsu.comprobarUsuario("admin", "administrador");
        ICategoriaImpl iCat = new ICategoriaImpl();

        // BARAJAS -----------------------------------------------------------------------------------------------------
        mainApp.anadirProducto("Baraja Dorada", "Una baraja de lujo con detalles dorados que reflejan elegancia y distinción.", U, iCat.getCategoriesFromDatabase().get(1), 20.0, true, "src/main/resources/img/Product/Barajas/Baraja001.jpg");
        mainApp.anadirProducto("Baraja Real", "Inspirada en la nobleza, esta baraja ofrece un diseño clásico con un toque regio.", U, iCat.getCategoriesFromDatabase().get(1), 20.0, true, "src/main/resources/img/Product/Barajas/Baraja002.jpg");
        mainApp.anadirProducto("Baraja Mini Red", "Una baraja compacta con un vibrante diseño en rojo, perfecta para llevar a todas partes", U, iCat.getCategoriesFromDatabase().get(1), 25.0, true, "src/main/resources/img/Product/Barajas/Baraja003.jpg");
        mainApp.anadirProducto("Baraja Gatuna", "Divertida y encantadora, esta baraja está decorada con adorables ilustraciones de gatos", U, iCat.getCategoriesFromDatabase().get(1), 30.0, true, "src/main/resources/img/Product/Barajas/Baraja004.jpg");
        mainApp.anadirProducto("Baraja Mitica", "Mitos y leyendas cobran vida en esta baraja con un arte fantástico y misterioso", U, iCat.getCategoriesFromDatabase().get(1), 45.0, true, "src/main/resources/img/Product/Barajas/Baraja005.jpg");

        // DIY ---------------------------------------------------------------------------------------------------------
        mainApp.anadirProducto("V8 Wood Motor", "Modelo de motor V8 en madera para armar, ideal para amantes de la mecánica y el bricolaje", U, iCat.getCategoriesFromDatabase().getFirst(), 20.0, true, "src/main/resources/img/Product/DIY/DIY001.jpg");
        mainApp.anadirProducto("Wood Cabinette", "Un pequeño gabinete de madera para armar, funcional y decorativo", U, iCat.getCategoriesFromDatabase().getFirst(), 20.0, true, "src/main/resources/img/Product/DIY/DIY002.jpg");
        mainApp.anadirProducto("Soporte Portatil", "Soporte de madera plegable y ergonómico, ideal para portátiles o tablets", U, iCat.getCategoriesFromDatabase().getFirst(), 25.0, true, "src/main/resources/img/Product/DIY/DIY003.jpg");
        mainApp.anadirProducto("Wooden Calendar", "Calendario perpetuo de madera para montar, una pieza elegante y funcional", U, iCat.getCategoriesFromDatabase().getFirst(), 30.0, true, "src/main/resources/img/Product/DIY/DIY004.jpg");
        mainApp.anadirProducto("Soporte Plantas", "Soporte modular de madera para plantas, ideal para interiores naturales", U, iCat.getCategoriesFromDatabase().getFirst(), 45.0, true, "src/main/resources/img/Product/DIY/DIY005.jpg");

        // ILUSTRACIONES -----------------------------------------------------------------------------------------------
        mainApp.anadirProducto("Dark Style", "Ilustración con estilo oscuro y elegante, ideal para quienes aman lo enigmático", U, iCat.getCategoriesFromDatabase().get(2), 20.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu001.jpg");
        mainApp.anadirProducto("Kittys", "Ternura total con esta ilustración repleta de gatitos adorables en distintos estilos", U, iCat.getCategoriesFromDatabase().get(2), 20.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu002.jpg");
        mainApp.anadirProducto("Floral", "Diseño floral colorido y delicado que transmite calma y belleza natural", U, iCat.getCategoriesFromDatabase().get(2), 25.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu003.jpg");
        mainApp.anadirProducto("Chibi Boy", "Ilustración estilo chibi de un simpático personaje lleno de personalidad", U, iCat.getCategoriesFromDatabase().get(2), 30.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu004.jpg");
        mainApp.anadirProducto("Afternoon City", "Escena urbana al atardecer, con una atmósfera cálida y melancólica", U, iCat.getCategoriesFromDatabase().get(2), 45.0, true, "src/main/resources/img/Product/Ilustraciones/Ilu005.jpg");

        // POCIONES ----------------------------------------------------------------------------------------------------
        mainApp.anadirProducto("Hermit Purple", "Poción inspirada en la energía mística, perfecta para momentos de concentración", U, iCat.getCategoriesFromDatabase().get(3), 20.0, true, "src/main/resources/img/Product/Pociones/Pocion001.jpg");
        mainApp.anadirProducto("ILove", "Una dulce poción rosa que evoca romance, cariño y emociones intensas", U, iCat.getCategoriesFromDatabase().get(3), 20.0, true, "src/main/resources/img/Product/Pociones/Pocion002.jpg");
        mainApp.anadirProducto("Midnight", "Oscura y misteriosa, esta poción refleja la esencia de la medianoche", U, iCat.getCategoriesFromDatabase().get(3), 25.0, true, "src/main/resources/img/Product/Pociones/Pocion003.jpg");
        mainApp.anadirProducto("Spider Venom", "Una poción potente y peligrosa, ideal para los amantes del riesgo", U, iCat.getCategoriesFromDatabase().get(3), 30.0, true, "src/main/resources/img/Product/Pociones/Pocion004.jpg");
        mainApp.anadirProducto("Hombre Lobo", "Poción salvaje y poderosa, con esencia nocturna y un toque sobrenatural", U, iCat.getCategoriesFromDatabase().get(3), 45.0, true, "src/main/resources/img/Product/Pociones/Pocion005.jpg");

    }

    public static void main(String[] args) {

        Font font = Font.loadFont(MainApplication.class.getResourceAsStream("/resources/fonts/Shrikhand-Regular.ttf"), 20);
        crearTablas();
        IMainAppImpl mainApp = new IMainAppImpl();
        //mainApp.generarCategoriasBase();
        //cargarBBDD();

        launch();
    }
}

