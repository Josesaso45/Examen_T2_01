package com.example.examen_t2_01

import retrofit2.Response // Importante cambiar Call por Response
import retrofit2.http.GET

interface TramiteService {
    @GET("configs")
    suspend fun getTramites(): Response<TramiteResponse> // Agregamos 'suspend' y cambiamos el retorno
}