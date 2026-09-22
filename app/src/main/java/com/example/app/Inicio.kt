package com.example.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.app.network.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        lifecycleScope.launch {
            // Verificar sesión activa en Supabase
            val session = SupabaseClientProvider.client.auth.currentSessionOrNull()
            if (session != null) {
                startActivity(Intent(this@Inicio, DashboardActivity::class.java))
            } else {
                startActivity(Intent(this@Inicio, MainActivity::class.java))
            }
            finish()
        }
    }
}