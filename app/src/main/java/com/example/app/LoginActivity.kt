package com.example.app

import android.content.Intent
import android.os.Bundle
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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
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

            if (usuarioTexto.isEmpty() || passwordTexto.isEmpty()) {
                Toast.makeText(this@LoginActivity, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usuarioTexto == emailRegistrado && passwordTexto == passwordRegistrada) {
                Toast.makeText(this@LoginActivity, R.string.success_welcome_test, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show()
            }
        }

        irCrearCuenta?.setOnClickListener {
            val intent = Intent(this@LoginActivity, Registro::class.java)
            startActivity(intent)
            finish()
        }
    }
}