package org.example.artefatto.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ComprasTest {

    private Compras compra;
    private Usuario usuario;
    private Producto producto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Juan");

        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombre("Producto A");

        compra = new Compras();
        compra.setId_compra(100L);
        compra.setId_usuario(usuario);
        compra.setId_producto(producto);
        compra.setPagado(true);
        compra.setFecha_compra(Date.valueOf("2024-12-01"));
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(100L, compra.getId_compra());
        assertEquals(usuario, compra.getId_usuario());
        assertEquals(producto, compra.getId_producto());
        assertTrue(compra.getPagado());
        assertEquals(Date.valueOf("2024-12-01"), compra.getFecha_compra());
    }

    @Test
    void testToStringContainsImportantFields() {
        String toString = compra.toString();
        assertTrue(toString.contains("id_compra=100"));
        assertTrue(toString.contains("isPagado=true"));
        assertTrue(toString.contains("2024-12-01"));
    }

    @Test
    void testDefaultConstructorInitializesPagadoToFalse() {
        Compras nuevaCompra = new Compras();
        assertFalse(nuevaCompra.getPagado());  // default is false
    }
}
