package com.es.segurofinal.models

data class Direccion(
    val calle: String,
    val num: Int,
    val provincia: String,
    val municipio: String,
    val cp: Int
)
