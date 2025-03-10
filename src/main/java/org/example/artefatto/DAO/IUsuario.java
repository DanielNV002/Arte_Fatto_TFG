package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Usuario;

import java.util.List;

public interface IUsuario {
    // Metodo para agregar un usuario
    void addUsuario(Usuario usuario);

    // Metodo para cargar la lista de Usuarios
    List<Usuario> loadUsuarios(List<Usuario> listaUsuarios);

    // Metodo para actualizar un usuario dado el id
    void updateUsuario(Long id, String nombre, String apellido, String direccion, String correo);

    // Metodo para eliminar un usuario dado el id
    void deleteUsuario(Long id);

    // Metodo para buscar un usuario según nombre o id
    Usuario searchUsuario(String nombre);

}
