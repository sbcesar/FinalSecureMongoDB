package com.es.segurofinal.dto

import com.es.segurofinal.models.Direccion
import com.es.segurofinal.models.Role

data class UsuarioRegisterDTO(
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val roles: Role?,
    val direccion: Direccion
)