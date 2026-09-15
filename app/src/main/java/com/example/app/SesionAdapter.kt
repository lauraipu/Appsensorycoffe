package com.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SesionAdapter(private var sesiones: List<Sesion>) :
    RecyclerView.Adapter<SesionAdapter.SesionViewHolder>() {

    class SesionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tv_nombre_sesion)
        val tvDetalle: TextView = view.findViewById(R.id.tv_detalle_sesion)
        val tvEstado: TextView = view.findViewById(R.id.tv_estado_sesion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SesionViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sesion, parent, false)
        return SesionViewHolder(vista)
    }

    override fun onBindViewHolder(holder: SesionViewHolder, position: Int) {
        val sesion = sesiones[position]
        holder.tvNombre.text = sesion.nombre
        holder.tvDetalle.text =
            "${sesion.formato} · ${sesion.fecha} · ${sesion.numeroMuestras} muestras"
        holder.tvEstado.text = sesion.estado
    }

    override fun getItemCount(): Int = sesiones.size

    fun actualizarLista(nuevaLista: List<Sesion>) {
        sesiones = nuevaLista
        notifyDataSetChanged()
    }
}
