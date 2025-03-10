package org.example.artefatto.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import javafx.scene.image.Image;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre", nullable = false, length = 50)
    @Pattern(regexp = "^[a-zA-Z0-9 ]{3,50}$", message = "El nombre de usuario solo puede contener letras, números, guion bajo (_) y guion medio (-), y debe tener entre 3 y 20 caracteres.")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "disponible", nullable = false)
    private boolean disponible;

    @Column(name = "imagen")
    private Image imagen;

    // Constructor sin parámetros (requerido por Hibernate)
    public Producto() {
    }

    // Constructor con todos los parámetros (excepto idProducto)
    public Producto(String nombre, Usuario usuario, String categoria, Double precio, boolean disponible, Image imagen) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.categoria = categoria;
        this.precio = precio;
        this.disponible = disponible;
        this.imagen = imagen;
    }

    // Getters y Setters
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Image getImagen(){
        return imagen;
    }

    public void setImagen(Image imagen){
        this.imagen = imagen;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", usuario=" + usuario.getIdUsuario() +  // Mostrar solo el ID del usuario
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", disponible=" + disponible +
                ", imagen=" + imagen +
                '}';
    }
}
