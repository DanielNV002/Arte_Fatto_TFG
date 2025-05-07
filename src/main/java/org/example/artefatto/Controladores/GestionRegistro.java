package org.example.artefatto.Controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
            new SceneSelector(GRegistro, "/org/example/artefatto/MainPage.fxml");
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Registro inválido", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta("Error", "Ha ocurrido un error inesperado.");
            e.printStackTrace();
        }
    }

    private Usuario crearUsuario() throws IOException {
        String username = TFieldUsername.getText().trim();
        String contrasena = TFieldContrasena.getText().trim();
        String contrasenaConfirm = TFieldContrasenaConfirm.getText().trim();
        String correo = TFieldCorreo.getText().trim();
        String nombre = TFieldName.getText().trim();
        String apellidos = TFieldApellidos.getText().trim();
        String direccion = TFieldDireccion.getText().trim();
        String imagen = "/img/Users/UserBaseProfile.png";

        // Validaciones de longitud mínima
        if (username.length() < 5 || contrasena.length() < 5 || contrasenaConfirm.length() < 5 ||
                correo.length() < 5 || nombre.length() < 5 || apellidos.length() < 5 || direccion.length() < 5) {
            throw new IllegalArgumentException("Todos los campos deben tener al menos 5 caracteres.");
        }

        // Validación de contraseñas
        if (!contrasena.equals(contrasenaConfirm)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        return new Usuario(null, apellidos, contrasena, correo, direccion, nombre, username, imagen, false);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
