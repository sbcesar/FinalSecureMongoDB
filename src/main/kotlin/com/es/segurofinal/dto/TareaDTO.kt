package com.es.segurofinal.dto

import com.es.segurofinal.models.Estado

data class TareaDTO(
    val titulo: String,
    val descripcion: String,
    val estado: Estado,
    val usuarioId: String   // El id del usuario, ya que no me parece correcto poner el usuario completo (exposicion de datos)
)