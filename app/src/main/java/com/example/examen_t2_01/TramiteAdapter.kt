package com.example.examen_t2_01

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_t2_01.databinding.ItemTramiteBinding

class TramiteAdapter(private val tramites: List<Tramite>) :
    RecyclerView.Adapter<TramiteAdapter.TramiteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramiteViewHolder {
        val binding = ItemTramiteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TramiteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TramiteViewHolder, position: Int) {
        holder.bind(tramites[position])
    }

    override fun getItemCount(): Int = tramites.size

    class TramiteViewHolder(private val binding: ItemTramiteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tramite: Tramite) {
            binding.tvTitulo.text = tramite.titulo
            
            // Ocultamos el subtitulo ya que en la imagen solo aparece el titulo principal
            binding.tvSubtitulo.visibility = android.view.View.GONE

            val context = binding.root.context

            // Mapeamos el nombre de la imagen que viene del JSON a tu carpeta drawable
            // El JSON suele enviar nombres como "ic_registro", "ic_aprobacion", etc.
            val imageResId = context.resources.getIdentifier(tramite.imagen, "drawable", context.packageName)

            if (imageResId != 0) {
                binding.ivTramite.setImageResource(imageResId)
            } else {
                Log.e("TramiteAdapter", "No se encontró recurso para: ${tramite.imagen}")
            }
        }
    }
}