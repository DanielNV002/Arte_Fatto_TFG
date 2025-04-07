package org.example.artefatto.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Arrays;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "El nombre de usuario solo puede contener letras, números, guion bajo (_) y guion medio (-), y debe tener entre 3 y 20 caracteres.")
    private String nombreUsuario;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "contrasena", nullable = false, length = 100)
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String contrasena;

    @Column(name = "correo", nullable = false, unique = true, length = 50)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El correo electrónico no es válido.")
    private String correo;

    @Column(name = "ProfileImage")
    private String profileImage;

    @Column(name = "conectado")
    private boolean conectado = false;

    // Constructor sin parámetros (requerido por Hibernate)
    public Usuario() {
    }

    // Constructor con todos los parámetros
    public Usuario(Long idUsuario, String apellido, String contrasena, String correo,
                   String direccion, String nombre, String nombreUsuario, String profileImage, boolean conectado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.contrasena = contrasena;
        this.correo = correo;
        this.profileImage = profileImage;
        this.conectado = conectado;
    }

    // Getters y Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", direccion='" + direccion + '\'' +
                ", correo='" + correo + '\'' +
                ", image='" + profileImage + '\'' +
                '}';
    }
}
