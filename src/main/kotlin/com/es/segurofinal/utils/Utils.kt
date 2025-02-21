package com.es.segurofinal.utils

import com.es.segurofinal.dto.UsuarioRegisterDTO
import com.es.segurofinal.error.exception.ValidationException

object Utils {

    fun verificarDatosUsuario(usuarioRegisterDTO: UsuarioRegisterDTO) {
        if (usuarioRegisterDTO.username.isBlank()
            || usuarioRegisterDTO.password.isBlank()
            || usuarioRegisterDTO.passwordRepeat.isBlank()
            || usuarioRegisterDTO.roles == null) {
            throw ValidationException("Uno o mas campos vacíos")
        }
    }
}