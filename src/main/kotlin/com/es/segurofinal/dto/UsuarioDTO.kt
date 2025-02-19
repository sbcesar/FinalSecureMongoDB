package com.es.segurofinal.dto

import com.es.segurofinal.models.Direccion
import com.es.segurofinal.models.Role
import com.es.segurofinal.models.Tarea

data class UsuarioDTO(
    val username: String,
    val role: Role,
)