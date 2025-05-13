package org.example.artefatto.Controladores;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.artefatto.DAO.IUsuarioImpl;
import org.example.artefatto.Entities.Usuario;
import org.example.artefatto.Util.SceneSelector;
import org.example.artefatto.Util.SessionManager;

import java.io.IOException;
import java.util.Optional;

public class GestionUsuario {
    public AnchorPane GUserPage;
    public Label UserProfileName;
    public Label IdProfileName;
    public Label GmailProfileName;
    public TextField TFieldName;
    public TextField TFieldSurname;
    public TextField TFieldEmail;
    public TextField TFieldDireccion;
    public TextField TFieldApodo;
    public ProgressIndicator spinner;

    @FXML
    public void initialize() {
        usuarioActivo();
    }

    @FXML
    private void handleButtonCategoryShopLinkClick() {
        spinner.setVisible(true); // Mostrar el spinner

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    // Realizar la operación que necesitas (cambiar de escena)
                    Platform.runLater(() -> {
                        try {
                            // Redirigir a la página de la tienda
                            new SceneSelector(GUserPage, "/org/example/artefatto/FirstShopPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner si falla
                    System.out.println("❌ Error al cargar la página");
                });
            }
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner después de la operación
                });
            }
        };
        new Thread(task).start(); // Ejecutar el task en un hilo aparte
    }


    private void usuarioActivo(){
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        System.out.println(iUsuario.actualUser().getNombreUsuario());

        if (UserProfileName != null) {
            UserProfileName.setText(iUsuario.actualUser().getNombreUsuario());
            IdProfileName.setText("ID:" + iUsuario.actualUser().getIdUsuario());
            GmailProfileName.setText(iUsuario.actualUser().getCorreo());
            TFieldName.setText(iUsuario.actualUser().getNombre());
            TFieldSurname.setText(iUsuario.actualUser().getApellido());
            TFieldEmail.setText(iUsuario.actualUser().getCorreo());
            TFieldDireccion.setText(iUsuario.actualUser().getDireccion());
            TFieldApodo.setText(iUsuario.actualUser().getNombreUsuario());
        } else {
            System.out.println("❌ Error: UserProfileName es null");
        }
    }

    @FXML
    private void guardarUsuario(){
        IUsuarioImpl iUsuario = new IUsuarioImpl();
        Usuario U = new Usuario();

        U.setIdUsuario(iUsuario.actualUser().getIdUsuario());
        U.setNombre(TFieldName.getText());
        U.setApellido(TFieldSurname.getText());
        U.setCorreo(TFieldEmail.getText());
        U.setDireccion(TFieldDireccion.getText());
        U.setNombreUsuario(TFieldApodo.getText());
        U.setConectado(iUsuario.actualUser().isConectado());

        iUsuario.actualizarUsuario(U);
    }

    @FXML
    private void cerrarSesion() {
        spinner.setVisible(true); // Mostrar el spinner
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    SessionManager sM = new SessionManager();
                    IUsuarioImpl iUsuario = new IUsuarioImpl();
                    sM.logOut(iUsuario.actualUser());

                    // Redirigir a la página principal después de cerrar sesión
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GUserPage, "/org/example/artefatto/MainPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner si falla
                    System.out.println("❌ Error al cerrar sesión");
                });
            }

            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner después de la operación
                });
            }
        };

        new Thread(task).start(); // Ejecutar el task en un hilo aparte
    }

    @FXML
    private void eliminarCuenta() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar la cuenta?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            // Aquí puedes poner la lógica de eliminación de cuenta si la hay
            IUsuarioImpl iUsuario = new IUsuarioImpl();
            iUsuario.eliminarCuenta(iUsuario.actualUser());

            // Cambiar de escena
            new SceneSelector(GUserPage, "/org/example/artefatto/MainPage.fxml");
        }
    }

    public void handleButtonCartShopLinkClick(ActionEvent actionEvent) throws IOException {
        spinner.setVisible(true); // Mostrar el spinner mientras se realiza la acción
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Platform.runLater(() -> {
                        try {
                            new SceneSelector(GUserPage, "/org/example/artefatto/CartPage.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner si falla
                    System.out.println("❌ Error al cambiar la escena");
                });
            }
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    spinner.setVisible(false); // Ocultar el spinner después de la transición
                });
            }
        };
        new Thread(task).start(); // Ejecutar el task en un hilo aparte
    }

}
