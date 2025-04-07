package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.SceneSelector;

import java.io.IOException;

public class GestionRegistro {

    @FXML
    private AnchorPane GRegistro;

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
    private void handleBackRegisterButtonClick() throws IOException {
        new SceneSelector(GRegistro, "/org/example/artefatto/MainPage.fxml");
    }

    @FXML
    private void handleRegistroUsuario() throws IOException {
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        try {
            iUsuario.addUsuario(crearUsuario());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        new SceneSelector(GRegistro, "/org/example/artefatto/MainPage.fxml");
    }

    private Usuario crearUsuario() throws IOException {
        String username = TFieldUsername.getText();
        String contrasena = TFieldContrasena.getText();
        String correo = TFieldCorreo.getText();
        String nombre = TFieldName.getText();
        String apellidos = TFieldApellidos.getText();
        String direccion = TFieldDireccion.getText();
        String imagen = "/img/Users/UserBaseProfile.png";

        return new Usuario(null, apellidos, contrasena, correo, direccion, nombre, username, imagen, false);
    }
}
