package com.example.examen_t2_01

data class TramiteResponse(
    val code: Int,
    val message: String,
    val data: List<Tramite>
)

data class Tramite(
    val titulo: String,
    val subtitulo: String,
    val imagen: String
)