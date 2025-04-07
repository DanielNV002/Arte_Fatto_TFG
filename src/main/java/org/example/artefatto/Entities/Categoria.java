package org.example.artefatto.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id_categoria;

    @Column(name = "nombre", nullable = false, length = 50)
    @Pattern(regexp = "^[a-zA-Z0-9 ]{3,50}$", message = "El nombre de usuario solo puede contener letras, números, guion bajo (_) y guion medio (-), y debe tener entre 3 y 20 caracteres.")
    private String nombre;

    @Lob
    @Column(name = "imagen")
    private String imagen;

    // Constructor
    public Categoria() {}

    public Categoria(Long id_categoria, String nombre, String imagen) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    // Getters y Setters

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    // Método toString()

    @Override
    public String toString() {
        return "Categoria{" +
                "id_categoria=" + id_categoria +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
