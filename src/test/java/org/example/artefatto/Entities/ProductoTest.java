package org.example.artefatto.Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    private Producto producto;
    private Usuario usuario;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Vendedor");

        categoria = new Categoria();
        categoria.setId_categoria(10L);
        categoria.setNombre("Electrónica");

        producto = new Producto();
        producto.setIdProducto(200L);
        producto.setNombre("Smartphone");
        producto.setDescripcion("Un teléfono inteligente");
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);
        producto.setPrecio(299.99);
        producto.setDisponible(true);
        producto.setImagen("imagen.png");
        producto.setActive(true);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(200L, producto.getIdProducto());
        assertEquals("Smartphone", producto.getNombre());
        assertEquals("Un teléfono inteligente", producto.getDescripcion());
        assertEquals(usuario, producto.getUsuario());
        assertEquals(categoria, producto.getCategoria());
        assertEquals(299.99, producto.getPrecio());
        assertTrue(producto.isDisponible());
        assertEquals("imagen.png", producto.getImagen());
        assertTrue(producto.getActive());
    }

    @Test
    void testToStringContainsImportantData() {
        String toString = producto.toString();
        assertTrue(toString.contains("Smartphone"));
        assertTrue(toString.contains("Electrónica"));
        assertTrue(toString.contains("299.99"));
    }

    @Test
    void testDefaultConstructorInitializesInactive() {
        Producto nuevoProducto = new Producto();
        assertFalse(nuevoProducto.getActive());  // debe ser false por defecto
    }
}
