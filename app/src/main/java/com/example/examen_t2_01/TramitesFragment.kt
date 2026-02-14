package com.example.examen_t2_01

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen_t2_01.databinding.FragmentTramitesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TramitesFragment : Fragment() {

    private var _binding: FragmentTramitesBinding? = null
    private val binding get() = _binding!!

    private lateinit var tramiteAdapter: TramiteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTramitesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Configurar el RecyclerView
        setupRecyclerView()

        // 2. Iniciar la carga de datos
        fetchTramites()
    }

    private fun setupRecyclerView() {
        binding.rvTramites.layoutManager = LinearLayoutManager(requireContext())
        // Inicializamos con una lista vacía
        tramiteAdapter = TramiteAdapter(emptyList())
        binding.rvTramites.adapter = tramiteAdapter
    }

    private fun fetchTramites() {
        // Mostrar barra de progreso si la tienes en tu XML
        binding.progressBar.visibility = View.VISIBLE

        // Configuración de Retrofit directamente para el ejercicio
        val retrofit = Retrofit.Builder()
            .baseUrl("https://05d0a0f6-fc0b-4105-96f7-748e7a92e611.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TramiteService::class.java)

        // Usamos lifecycleScope para que la petición se cancele si cierras el fragmento
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = service.getTramites()

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE

                    if (response.isSuccessful && response.body() != null) {
                        val listaTramites = response.body()!!.data
                        // Actualizamos el adapter con los nuevos datos
                        tramiteAdapter = TramiteAdapter(listaTramites)
                        binding.rvTramites.adapter = tramiteAdapter
                    } else {
                        showError("Error en el servidor: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Log.e("TramitesFragment", "Error de red", e)
                    showError("Error de conexión. Revisa tu internet.")
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}