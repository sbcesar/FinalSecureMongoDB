package com.es.segurofinal.domain

data class DatosMunicipios(
    val update_date: String,
    val size: Int,
    val data: List<Municipio>?,
    val warning: String?,
    val error: String?
)