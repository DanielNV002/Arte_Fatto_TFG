package org.example.artefatto.DAO;

import org.example.artefatto.Entities.Compras;

import java.util.List;

public interface ICompras {

    void anadirCompra(Compras compra);

    List<Compras> listaDeCompras(Long id_usuario);

    void compraCompletada(Compras compra);

    void eliminarCompra(Compras compra);

}
