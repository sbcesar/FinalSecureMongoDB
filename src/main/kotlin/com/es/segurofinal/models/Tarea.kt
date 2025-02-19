package com.es.segurofinal.models

data class Tarea(
    val titulo: String,
    val descricao: String,
    val estado: Estado,
    val usuario: Usuario
)