package org.example.artefatto.Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    @Test
    void testConstructorYGettersSetters() {
        Categoria categoria = new Categoria(1L, "Electrónica", "imagen.jpg", true);

        assertEquals(1L, categoria.getId_categoria());
        assertEquals("Electrónica", categoria.getNombre());
        assertEquals("imagen.jpg", categoria.getImagen());
        assertTrue(categoria.getActive());
    }

    @Test
    void testToString() {
        Categoria categoria = new Categoria(1L, "Electrónica", "imagen.jpg", true);
        String expectedToString = "Categoria{id_categoria=1, nombre='Electrónica', imagen='imagen.jpg', isActive=true}";

        assertEquals(expectedToString, categoria.toString());
    }
}
