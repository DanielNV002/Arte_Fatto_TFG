package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import java.util.List;

public interface ICategoria {

    List<Categoria> getCategoriesFromDatabase();

    void actualizarCategoria(Categoria categoria);

    Categoria actualCategoria();
}
