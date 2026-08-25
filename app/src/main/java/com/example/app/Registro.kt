package com.example.app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Registro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }


        val nombre = findViewById<EditText>(R.id.et_nombre)
        val email = findViewById<EditText>(R.id.et_email)
        val telefono = findViewById<EditText>(R.id.et_phone)
        val panel = findViewById<EditText>(R.id.et_panel)
        val password = findViewById<EditText>(R.id.et_password)
        val confirmarPassword = findViewById<EditText>(R.id.et_confirmpassword)
        val aceptarDatos = findViewById<CheckBox>(R.id.cb_aceptar_datos)
        val btnCrearCuenta = findViewById<Button>(R.id.btn_crearcuenta)
        val tvLogin = findViewById<TextView>(R.id.tv_login)



        btnCrearCuenta.setOnClickListener {

            val nombreTexto = nombre.text.toString().trim()
            val emailTexto = email.text.toString().trim()
            val telefonoTexto = telefono.text.toString().trim()
            val panelTexto = panel.text.toString().trim()
            val passwordTexto = password.text.toString()
            val confirmarTexto = confirmarPassword.text.toString()

            if (nombreTexto.isEmpty()) {
                nombre.error = "Ingresa tu nombre"
                nombre.requestFocus()
                return@setOnClickListener
            }

            if (nombreTexto.length < 3) {
                nombre.error = "El nombre debe tener mínimo 3 caracteres"
                nombre.requestFocus()
                return@setOnClickListener
            }

            if (!nombreTexto.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))) {
                nombre.error = "El nombre solo puede contener letras"
                nombre.requestFocus()
                return@setOnClickListener
            }

            if (emailTexto.isEmpty()) {
                email.error = "Ingresa tu correo"
                email.requestFocus()
                return@setOnClickListener
            }

            if (emailTexto.contains(" ")) {
                email.error = "El correo no puede contener espacios"
                email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailTexto).matches()) {
                email.error = "Ingresa un correo válido"
                email.requestFocus()
                return@setOnClickListener
            }

            if (telefonoTexto.isEmpty()) {
                telefono.error = "Ingresa tu teléfono"
                telefono.requestFocus()
                return@setOnClickListener
            }

            if (!telefonoTexto.matches(Regex("^[0-9]{10}$"))) {
                telefono.error = "El teléfono debe tener 10 dígitos"
                telefono.requestFocus()
                return@setOnClickListener
            }

            if (panelTexto.isEmpty()) {
                panel.error = "Ingresa el panel"
                panel.requestFocus()
                return@setOnClickListener
            }

            if (passwordTexto.isEmpty()) {
                password.error = "Ingresa una contraseña"
                password.requestFocus()
                return@setOnClickListener
            }

            if (passwordTexto.length < 8) {
                password.error =
                    "La contraseña debe tener mínimo 8 caracteres"
                password.requestFocus()
                return@setOnClickListener
            }

            if (passwordTexto.contains(" ")) {
                password.error =
                    "La contraseña no puede contener espacios"
                password.requestFocus()
                return@setOnClickListener
            }

            if (!passwordTexto.any { it.isUpperCase() }) {
                password.error =
                    "Debe contener al menos una letra mayúscula"
                password.requestFocus()
                return@setOnClickListener
            }

            if (!passwordTexto.any { it.isLowerCase() }) {
                password.error =
                    "Debe contener al menos una letra minúscula"
                password.requestFocus()
                return@setOnClickListener
            }

            if (!passwordTexto.any { it.isDigit() }) {
                password.error =
                    "Debe contener al menos un número"
                password.requestFocus()
                return@setOnClickListener
            }

            if (!passwordTexto.any { !it.isLetterOrDigit() }) {
                password.error =
                    "Debe contener al menos un carácter especial"
                password.requestFocus()
                return@setOnClickListener
            }



            if (confirmarTexto.isEmpty()) {
                confirmarPassword.error =
                    "Confirma tu contraseña"
                confirmarPassword.requestFocus()
                return@setOnClickListener
            }

            if (passwordTexto != confirmarTexto) {
                confirmarPassword.error =
                    "Las contraseñas no coinciden"
                confirmarPassword.requestFocus()
                return@setOnClickListener
            }

            if (!aceptarDatos.isChecked) {
                Toast.makeText(
                    this,
                    "Debes aceptar el tratamiento de datos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            Toast.makeText(
                this,
                "Cuenta creada correctamente",
                Toast.LENGTH_SHORT
            ).show()


            val intent = Intent(
                this@Registro,
                LoginActivity::class.java
            )

            intent.putExtra("USER_EMAIL", emailTexto)
            intent.putExtra("USER_PASSWORD", passwordTexto)
            startActivity(intent)
            finish()
        }



        tvLogin.setOnClickListener {
            val intent = Intent(
                this@Registro,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}