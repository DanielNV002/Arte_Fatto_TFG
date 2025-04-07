package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Categoria;
import org.example.artefatto.Entities.Producto;

import java.util.List;

public interface IProducto {

    List<Producto> obtenerProductos();

    List<Producto> getProductosDesdeBD();

    List<Producto> getProductosPorCategoria(Categoria categoria);
}
