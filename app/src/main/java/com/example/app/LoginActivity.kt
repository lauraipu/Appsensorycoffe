package com.example.app

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom)
            insets
        }


        val usuario = findViewById<EditText>(R.id.et_usuario)
        val password = findViewById<EditText>(R.id.et_password)

        val btnEntrar = findViewById<Button>(R.id.btn_login)
        val irCrearCuenta = findViewById<TextView>(R.id.tv_ir_registro)

        val emailRegistrado = intent.getStringExtra("USER_EMAIL")
        val passwordRegistrada = intent.getStringExtra("USER_PASSWORD")

        if (!emailRegistrado.isNullOrEmpty()) {
            usuario.setText(emailRegistrado)
        }

        btnEntrar.setOnClickListener {

            val usuarioTexto = usuario.text.toString().trim()
            val passwordTexto = password.text.toString()


            if (usuarioTexto.isEmpty()) {
                usuario.error = "Ingresa tu correo"
                usuario.requestFocus()
                return@setOnClickListener
            }

            if (usuarioTexto.contains(" ")) {

                usuario.error =
                    "El correo no puede contener espacios"
                usuario.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(usuarioTexto).matches()) {
                usuario.error =
                    "Ingresa un correo electrónico válido"
                usuario.requestFocus()
                return@setOnClickListener
            }

            if (passwordTexto.isEmpty()) {
                password.error =
                    "Ingresa tu contraseña"
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
                    "Debe contener al menos una mayúscula"
                password.requestFocus()
                return@setOnClickListener
            }

            if (!passwordTexto.any { it.isLowerCase() }) {
                password.error =
                    "Debe contener al menos una minúscula"
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


            if (
                usuarioTexto == emailRegistrado &&
                passwordTexto == passwordRegistrada
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "¡Bienvenido!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                Toast.makeText(
                    this@LoginActivity,
                    "Correo o contraseña incorrectos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        irCrearCuenta.setOnClickListener {

            val intent =
                Intent(this@LoginActivity,Registro::class.java)
            startActivity(intent)
            finish()
        }
    }
}