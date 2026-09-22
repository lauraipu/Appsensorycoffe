package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.app.network.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDto(
    val id: String,
    val nombre: String,
    val telefono: String,

)

class Registro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etNombre = findViewById<EditText>(R.id.et_nombre)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPhone = findViewById<EditText>(R.id.et_phone)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etConfirmPassword = findViewById<EditText>(R.id.et_confirmpassword)
        val cbAceptarDatos = findViewById<CheckBox>(R.id.cb_aceptar_datos)
        val btnCrearCuenta = findViewById<Button>(R.id.btn_crearcuenta)
        val tvLogin = findViewById<TextView>(R.id.tv_login)

        btnCrearCuenta.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val pass = etPassword.text.toString()
            val confirmPass = etConfirmPassword.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbAceptarDatos.isChecked) {
                Toast.makeText(this, "Debes aceptar la política de tratamiento de datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Disable button to prevent rapid multiple taps
            btnCrearCuenta.isEnabled = false

            lifecycleScope.launch {
                try {
                    // 1. Crear usuario en Auth
                    val user = SupabaseClientProvider.client.auth.signUpWith(Email) {
                        this.email = email
                        this.password = pass
                    }

                    val userId = user?.id ?: SupabaseClientProvider.client.auth.currentUserOrNull()?.id

                    // 2. Insertar en public.usuarios
                    if (userId != null) {
                        val usuarioProfile = UsuarioDto(
                            id = userId,
                            nombre = nombre,
                            telefono = phone,
                        )
                        SupabaseClientProvider.client.postgrest["usuarios"].insert(usuarioProfile)
                    }

                    Toast.makeText(this@Registro, "¡Cuenta creada exitosamente!", Toast.LENGTH_SHORT).show()

                    // REDIRECCIÓN INMEDIATA AL LOGIN
                    val intent = Intent(this@Registro, LoginActivity::class.java)
                    // Limpia la pila para que el usuario no pueda regresar al formulario presionando 'Atrás'
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK 
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    // Re-enable button if registration fails
                    btnCrearCuenta.isEnabled = true
                    Toast.makeText(this@Registro, "Error al registrar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        tvLogin?.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}