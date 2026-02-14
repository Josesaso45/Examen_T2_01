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
            binding.tvSubtitulo.text = tramite.subtitulo
            
            val context = binding.root.context
            
            // Lógica para manejar los nombres del JSON vs los nombres de tus archivos
            // Si el JSON dice "img_registro" y tu archivo es "ic_registro"
            val imageResourceName = tramite.imagen.replace("img_", "ic_")
            
            var imageResId = getDrawableId(context, imageResourceName)
            
            // Intento secundario con el nombre original por si acaso
            if (imageResId == 0) {
                imageResId = getDrawableId(context, tramite.imagen)
            }

            if (imageResId != 0) {
                binding.ivTramite.setImageResource(imageResId)
            } else {
                Log.e("TramiteAdapter", "No se encontró recurso para: ${tramite.imagen} ni para $imageResourceName")
                // Puedes poner un icono genérico aquí si quieres:
                // binding.ivTramite.setImageResource(android.R.drawable.ic_menu_report_image)
            }
        }

        private fun getDrawableId(context: Context, name: String): Int {
            return context.resources.getIdentifier(name, "drawable", context.packageName)
        }
    }
}