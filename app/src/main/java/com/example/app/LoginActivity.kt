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
import androidx.lifecycle.lifecycleScope
import com.example.app.network.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

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

        btnEntrar.setOnClickListener {
            val usuarioTexto = usuario.text.toString().trim()
            val passwordTexto = password.text.toString()

            if (usuarioTexto.isEmpty() || passwordTexto.isEmpty()) {
                Toast.makeText(this@LoginActivity, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Inicio de Sesión asíncrono con Supabase Auth
            lifecycleScope.launch {
                try {
                    SupabaseClientProvider.client.auth.signInWith(Email) {
                        email = usuarioTexto
                        this.password = passwordTexto
                    }
                    Toast.makeText(this@LoginActivity, "¡Bienvenido de nuevo!", Toast.LENGTH_SHORT).show()

                    // Redirigir al Dashboard principal
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        irCrearCuenta?.setOnClickListener {
            val intent = Intent(this@LoginActivity, Registro::class.java)
            startActivity(intent)
            finish()
        }
    }
}