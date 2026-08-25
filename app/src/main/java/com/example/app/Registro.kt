package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNombre = findViewById<EditText>(R.id.et_nombre)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etPanel = findViewById<EditText>(R.id.et_panel)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etConfirmPassword = findViewById<EditText>(R.id.et_confirmpassword)

        val cbAceptarDatos = findViewById<CheckBox>(R.id.cb_aceptar_datos)
        val tvTratamientoDatos = findViewById<TextView>(R.id.tv_tratamiento_datos)

        val btnCrearCuenta = findViewById<Button>(R.id.btn_crearcuenta)
        val tvLogin = findViewById<TextView>(R.id.tv_login)

        tvTratamientoDatos.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.title_data_privacy)
                .setMessage(R.string.message_data_privacy)
                .setPositiveButton(R.string.btn_understood) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        btnCrearCuenta.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val panel = etPanel.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || phone.isEmpty() || panel.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, R.string.error_passwords_mismatch, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbAceptarDatos.isChecked) {
                Toast.makeText(this, R.string.error_accept_terms, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Toast.makeText(this, R.string.success_account_created, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("USER_EMAIL", email)
            intent.putExtra("USER_PASSWORD", password)
            startActivity(intent)
            finish()
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

