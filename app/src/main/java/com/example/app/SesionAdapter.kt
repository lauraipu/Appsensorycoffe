package com.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.Serializable

@Serializable
data class SesionDto(
    val id: String,
    val nombre: String,
    val formato_cva: String? = null,
    val numero_muestras: Int? = 0,
    val catadores_objetivo: Int? = 0,
    val organizador_participa: Boolean? = false,
    val codigo_sesion: String? = null,
    val organizador_id: String? = null,
    val estado: String? = null,
    val notas: String? = null,
    val creado_en: String? = null
)

class SesionAdapter(
    private val listaSesiones: List<SesionDto>,
    private val onItemClick: (SesionDto) -> Unit
) : RecyclerView.Adapter<SesionAdapter.SesionViewHolder>() {

    class SesionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tv_nombre_sesion)
        val tvEstado: TextView = itemView.findViewById(R.id.tv_estado_sesion)
        val tvFormato: TextView = itemView.findViewById(R.id.tv_formato_sesion)
        val tvDetalle: TextView = itemView.findViewById(R.id.tv_detalle_sesion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SesionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sesion, parent, false)
        return SesionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SesionViewHolder, position: Int) {
        val sesion = listaSesiones[position]
        holder.tvNombre.text = sesion.nombre
        
        val estadoTexto = sesion.estado?.replace("_", " ")?.uppercase() ?: "DESCONOCIDO"
        holder.tvEstado.text = "• $estadoTexto"
        
        holder.tvFormato.text = sesion.formato_cva ?: "CVA"
        holder.tvDetalle.text = "Código: ${sesion.codigo_sesion ?: "N/A"} | Muestras: ${sesion.numero_muestras ?: 0}"

        holder.itemView.setOnClickListener { onItemClick(sesion) }
    }

    override fun getItemCount(): Int = listaSesiones.size
}
