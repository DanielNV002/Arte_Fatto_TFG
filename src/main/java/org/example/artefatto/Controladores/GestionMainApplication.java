package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.MainApplication;
import org.example.artefatto.Util.ImageUtils;
import org.example.artefatto.Util.SceneSelector;

import java.io.IOException;
import java.util.Objects;


public class GestionMainApplication {
    @FXML
    private AnchorPane GIniciarSesion;
    @FXML
    private AnchorPane GRegistro;
    @FXML
    private AnchorPane GFirstShopPage;

    @FXML
    private TextField TFieldUsername;
    @FXML
    private TextField TFieldName;
    @FXML
    private TextField TFieldContrasena;
    @FXML
    private TextField TFieldCorreo;
    @FXML
    private TextField TFieldApellidos;
    @FXML
    private TextField TFieldDireccion;
    @FXML
    private TextField TFieldContrasenaConfirm;

    @FXML
    private PasswordField TFieldContrasena1;

    @FXML
    private void initialize() {

    }

    /*------------------ CAMBIAR PANTALLAS O SALIR--------------------------------------------------------*/
    @FXML
    private void handleExitButtonClick() {
        // Cerrar la aplicación usando Platform.exit() o primaryStage.close()
        Platform.exit();  // Esto cierra toda la aplicación
        System.exit(0);  // Esto garantiza que la aplicación termine correctamente
    }
    @FXML
    private void handleBackRegisterButtonClick() throws IOException {
        new SceneSelector(GRegistro, "/org/example/artefatto/MainPage.fxml");
    }
    @FXML
    private void handleButtonRegistroLinkClick() throws IOException {
        new SceneSelector(GIniciarSesion, "/org/example/artefatto/RegisterPage.fxml");
    }
    @FXML
    private void handleButtonFirstShopLinkClick() throws IOException {
        new SceneSelector(GFirstShopPage, "/org/example/artefatto/MainPage.fxml");
    }

    /*------------------- CONTROLES INICIO SESION ------------------------------------------------------------------------*/
    @FXML
    private void iniciarSesion() throws IOException {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario actualUser = iUsuario.comprobarUsuario(TFieldUsername.getText(), TFieldContrasena.getText());
        if(actualUser == null){
            TFieldUsername.setText("DATOS INCORRECTOS");
            TFieldContrasena1.setText("");
        }else if(actualUser.getNombreUsuario().equals(TFieldUsername.getText()) && actualUser.getContrasena().equals(TFieldContrasena.getText())){
            new SceneSelector(GIniciarSesion, "/org/example/artefatto/FirstShopPage.fxml");
        }
    }

    /*------------------- CONTROLES REGISTRO ------------------------------------------------------------------------*/

    @FXML
    private void handleRegistroUsuario() throws IOException {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        iUsuario.addUsuario(crearUsuario());
        new SceneSelector(GRegistro, "/org/example/artefatto/MainPage.fxml");

    }

    private Usuario crearUsuario() throws IOException {
        String username = TFieldUsername.getText();
        String contrasena = TFieldContrasena.getText();
        String correo = TFieldCorreo.getText();
        String nombre = TFieldName.getText();
        String apellidos = TFieldApellidos.getText();
        String direccion = TFieldDireccion.getText();
        Image image = new Image(Objects.requireNonNull(MainApplication.class.getResource("/img/Users/UserBaseProfile.png")).toExternalForm());
        byte[] imagentoByte =  ImageUtils.imageToBytes(image);
        System.out.println(imagentoByte.length);
        return new Usuario(null, apellidos, contrasena, correo, direccion, nombre, username, ImageUtils.imageToBytes(image));
    }
}