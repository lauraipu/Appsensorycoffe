package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.network.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class DashboardActivity : AppCompatActivity() {

    private lateinit var rvSesiones: RecyclerView
    private lateinit var adapter: SesionAdapter
    private lateinit var tvTotalSesiones: TextView
    private lateinit var tvTotalMuestras: TextView
    private lateinit var tvNombreUsuario: TextView
    private lateinit var etCodigoUnirse: EditText
    private lateinit var btnUnirse: Button
    private lateinit var tvCerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Vincular componentes de la interfaz
        rvSesiones = findViewById(R.id.rv_sesiones)
        rvSesiones.layoutManager = LinearLayoutManager(this)

        tvTotalSesiones = findViewById(R.id.tv_total_sesiones)
        tvTotalMuestras = findViewById(R.id.tv_total_muestras)
        tvNombreUsuario = findViewById(R.id.tv_nombre_usuario)
        etCodigoUnirse = findViewById(R.id.et_codigo_unirse)
        btnUnirse = findViewById(R.id.btn_unirse)
        tvCerrarSesion = findViewById(R.id.tv_cerrar_sesion)

        // Mostrar email o metadata del usuario autenticado
        val usuarioActual = SupabaseClientProvider.client.auth.currentUserOrNull()
        tvNombreUsuario.text = usuarioActual?.email ?: "Usuario"

        // Cargar sesiones de la base de datos
        cargarSesiones()

        // Acción: Unirme a una sesión mediante la función RPC 'unirse_sesion'
        btnUnirse.setOnClickListener {
            val codigo = etCodigoUnirse.text.toString().trim()
            if (codigo.isEmpty()) {
                Toast.makeText(this, "Ingresa un código válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            unirseASesion(codigo)
        }

        // Acción: Cerrar Sesión
        tvCerrarSesion.setOnClickListener {
            lifecycleScope.launch {
                try {
                    SupabaseClientProvider.client.auth.signOut()
                    val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@DashboardActivity, "Error al cerrar sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarSesiones() {
        lifecycleScope.launch {
            try {
                // Supabase filtra automáticamente según la política RLS 'ver_sesiones_propias'
                val sesiones = SupabaseClientProvider.client.postgrest["sesiones"]
                    .select()
                    .decodeList<SesionDto>()

                adapter = SesionAdapter(sesiones) { sesionSeleccionada ->
                    Toast.makeText(
                        this@DashboardActivity,
                        "Sesión seleccionada: ${sesionSeleccionada.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                rvSesiones.adapter = adapter

                // Actualizar métricas reales en el Dashboard
                tvTotalSesiones.text = sesiones.size.toString()
                val totalMuestras = sesiones.sumOf { it.numero_muestras ?: 0 }
                tvTotalMuestras.text = totalMuestras.toString()

            } catch (e: Exception) {
                Toast.makeText(
                    this@DashboardActivity,
                    "Error al cargar datos reales: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun unirseASesion(codigo: String) {
        btnUnirse.isEnabled = false
        lifecycleScope.launch {
            try {
                // Invocación de la función SQL 'unirse_sesion(p_codigo)'
                val params = buildJsonObject {
                    put("p_codigo", codigo)
                }
                
                SupabaseClientProvider.client.postgrest.rpc("unirse_sesion", params)

                Toast.makeText(this@DashboardActivity, "¡Te has unido exitosamente!", Toast.LENGTH_SHORT).show()
                etCodigoUnirse.text.clear()
                
                // Recargar el listado para ver la nueva sesión asignada
                cargarSesiones()

            } catch (e: Exception) {
                Toast.makeText(this@DashboardActivity, e.message ?: "Error al unirse a la sesión", Toast.LENGTH_LONG).show()
            } finally {
                btnUnirse.isEnabled = true
            }
        }
    }
}
