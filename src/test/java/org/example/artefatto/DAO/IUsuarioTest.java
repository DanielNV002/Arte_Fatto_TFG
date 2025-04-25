package org.example.artefatto.DAO;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class IUsuarioTest {
        @Test
        public void testPasswordMismatch() {
            String password = "Password123";
            String confirmPassword = "Password321";
            assertNotEquals(password, confirmPassword, "Passwords should not match");
        }
}