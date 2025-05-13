package org.example.artefatto.Entities;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorAndGettersSetters() {
        Usuario user = new Usuario(1L, "García", "pass123456", "test@example.com",
                "Calle Falsa 123", "Juan", "juan_1990", "profile.png", true);

        assertEquals(1L, user.getIdUsuario());
        assertEquals("García", user.getApellido());
        assertEquals("Juan", user.getNombre());
        assertEquals("juan_1990", user.getNombreUsuario());
        assertEquals("pass123456", user.getContrasena());
        assertEquals("test@example.com", user.getCorreo());
        assertEquals("Calle Falsa 123", user.getDireccion());
        assertEquals("profile.png", user.getProfileImage());
        assertTrue(user.isConectado());
    }

    @Test
    void testToString() {
        Usuario user = new Usuario(1L, "Pérez", "clave123", "perez@mail.com",
                "Av. Siempreviva", "Luis", "luis_pz", "img.jpg", false);

        String expected = "Usuario{idUsuario=1, nombre='Luis', apellido='Pérez', nombreUsuario='luis_pz', direccion='Av. Siempreviva', correo='perez@mail.com', image='img.jpg'}";
        assertEquals(expected, user.toString());
    }

    @Test
    void testValidUsuario() {
        Usuario user = new Usuario();
        user.setNombre("Ana");
        user.setApellido("Martínez");
        user.setNombreUsuario("ana123");
        user.setContrasena("miClaveSegura");
        user.setCorreo("ana@mail.com");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "No debe haber violaciones de validación");
    }

    @Test
    void testInvalidNombreUsuario() {
        Usuario user = new Usuario();
        user.setNombreUsuario("no@válido");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals("El nombre de usuario solo puede contener letras, números, guion bajo (_) y guion medio (-), y debe tener entre 3 y 20 caracteres.",
                violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidContrasena() {
        Usuario user = new Usuario();
        user.setContrasena("123"); // muy corta

        Set<ConstraintViolation<Usuario>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals("La contraseña debe tener al menos 8 caracteres.",
                violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidCorreo() {
        Usuario user = new Usuario();
        user.setCorreo("correo-malformado");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals("El correo electrónico no es válido.",
                violations.iterator().next().getMessage());
    }
}
