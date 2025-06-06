package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Usuario;

public interface IMainApp {

    void generarCategoriasBase();
    void anadirProducto(String nombre, String descripcion, Usuario usuario, Categoria categoria, double precio, boolean disponible, String imagen);
    void anadirUsuario(Long idUsuario, String apellido, String contrasena, String correo, String direccion, String nombre, String nombreUsuario, String profileImage, boolean conectado);
}
