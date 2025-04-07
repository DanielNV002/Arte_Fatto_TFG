package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Usuario;

public interface IMainApp {

    void generarCategoriasBase();
    void anadirProducto(String nombre, Usuario usuario, Categoria categoria, double precio, boolean disponible, String imagen);
}
