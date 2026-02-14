package com.example.examen_t2_01

import retrofit2.Call
import retrofit2.http.GET

interface TramiteService {
    @GET("configs")
    fun getTramites(): Call<TramiteResponse>
}