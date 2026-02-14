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
        setupRecyclerView()
        fetchTramites()
    }

    private fun setupRecyclerView() {
        binding.rvTramites.layoutManager = LinearLayoutManager(requireContext())
        tramiteAdapter = TramiteAdapter(emptyList())
        binding.rvTramites.adapter = tramiteAdapter
    }

    private fun fetchTramites() {
        binding.progressBar.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://05d0a0f6-fc0b-4105-96f7-748e7a92e611.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TramiteService::class.java)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = service.getTramites()

                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null && response.body()!!.data.isNotEmpty()) {
                        tramiteAdapter = TramiteAdapter(response.body()!!.data)
                        binding.rvTramites.adapter = tramiteAdapter
                    } else {
                        // SI LA API FALLA O ESTÁ VACÍA, CARGAMOS DATOS LOCALES PARA QUE FUNCIONE YA
                        loadLocalData()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Log.e("TramitesFragment", "Error: ${e.message}")
                    loadLocalData() // Cargar locales en caso de error de red
                }
            }
        }
    }

    private fun loadLocalData() {
        val listaLocal = listOf(
            Tramite("Alertas y notificación", "", "ic_registro"),
            Tramite("Clave dinámica", "", "ic_aprobacion"),
            Tramite("Biometría digital", "", "ic_validacion")
        )
        tramiteAdapter = TramiteAdapter(listaLocal)
        binding.rvTramites.adapter = tramiteAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}