#  PacamaraQs

**PacamaraQs** es una aplicación nativa de Android desarrollada en Kotlin que ofrece un flujo completo de autenticación de usuarios (Registro, Inicio de Sesión y Pantalla Principal). 

El proyecto implementa buenas prácticas de desarrollo móvil, diseño inmersivo y validación dinámica de datos en tiempo de ejecución.

---

##  Características Principales

* **Menú de Bienvenida (`MainActivity`):** Punto de entrada intuitivo para dirigir al usuario hacia el login o la creación de cuenta.
* **Registro de Usuario (`Registro`):** 
  * Formulario completo (Nombre, Correo, Teléfono, Panel/Laboratorio, Contraseña).
  * Validaciones integradas (Campos vacíos, coincidencia de contraseñas y aceptación obligatoria de términos).
  * Diálogo de privacidad (`AlertDialog`) para consulta de Habeas Data.
  * Transferencia de datos segura entre actividades utilizando `Intent` y `putExtra()`.
* **Inicio de Sesión (`LoginActivity`):** 
  * Autenticación dinámica que valida credenciales en tiempo real.
  * Autocompletado inteligente del correo si el usuario proviene del registro.
* **Pantalla Principal (`Inicio`):** Vista principal (Dashboard) lista para desplegar información tras una autenticación exitosa.

---

## Tecnologías y Herramientas

* **Lenguaje:** Kotlin
* **SDK Mínimo / Target:** Target SDK 37 (Android 15)
* **Java Version:** Java 17
* **Arquitectura de UI:** XML (`ConstraintLayout`, `LinearLayout`, `CardView`, `MaterialButton`)
* **Diseño:** System UI Inset Handling (`Edge-to-Edge`)

---

##  Estructura del Proyecto

```text
com.example.app/
├── MainActivity.kt      # Pantalla de bienvenida / Menú inicial
├── Registro.kt          # Formulario de registro y validación
├── LoginActivity.kt     # Pantalla de inicio de sesión y verificación
└── Inicio.kt            # Dashboard principal de la aplicación
