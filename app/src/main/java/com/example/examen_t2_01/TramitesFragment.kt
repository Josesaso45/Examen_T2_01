package com.example.examen_t2_01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.examen_t2_01.databinding.FragmentTramitesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TramitesFragment : Fragment() {

    private var _binding: FragmentTramitesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTramitesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarTramites()
    }

    private fun cargarTramites() {
        binding.progressBar.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://05d0a0f6-fc0b-4105-96f7-748e7a92e611.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TramiteService::class.java)

        service.getTramites().enqueue(object : Callback<TramiteResponse> {
            override fun onResponse(call: Call<TramiteResponse>, response: Response<TramiteResponse>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val listado = response.body()?.data ?: emptyList()
                    binding.rvTramites.adapter = TramiteAdapter(listado)
                } else {
                    Toast.makeText(context, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TramiteResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}