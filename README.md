# 🎨 Arte Fatto

**Arte Fatto** es una tienda de arte y artículos hechos a mano que funciona con una base de datos local.  
Ideal para explorar productos únicos y personalizados, gestionando usuarios, productos y compras a través de una interfaz sencilla e intuitiva.

---

## 📦 Requisitos

Antes de iniciar la aplicación, asegúrate de contar con:

- Una base de datos local llamada **`ArteFattoDB`**  
  Esta debería incluirse en la descarga del proyecto. Si no aparece correctamente, puedes regenerarla fácilmente (ver instrucciones abajo).

---

## 🚀 Instrucciones de Inicio

Si necesitas regenerar la base de datos, sigue estos pasos:

1. Abre el archivo `MainApplication.java`.
2. Dirígete a las líneas **98 y 99**:

   ```java
   //mainApp.generarCategoriasBase();
   //cargarBBDD();

3. Descomenta temporalmente las líneas:
   ```java
   mainApp.generarCategoriasBase();
   cargarBBDD();
4. Ejecuta el programa una sola vez para reiniciar la base de datos.
5. Vuelve a comentar esas líneas para evitar sobrescribir datos en futuras ejecuciones:
   ```java
   //mainApp.generarCategoriasBase();
   //cargarBBDD();

---

## 🐢 Notas de Rendimiento

⚠️ La aplicación puede presentar tiempos de respuesta lentos debido al manejo local de datos con la base de datos.
Se recomienda tener paciencia al navegar.

---

## ✅ Funcionalidades Principales

🔐 Registro e inicio de sesión de usuarios

👤 Navegación como invitado

🧺 Carrito de compras

🖼️ Interfaz visual con tarjetas para categorías y productos

---

## 📜 Licencia

Este proyecto es de uso libre para fines educativos o personales.
