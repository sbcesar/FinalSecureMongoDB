package com.es.segurofinal.utils

import com.es.segurofinal.dto.UsuarioRegisterDTO
import com.es.segurofinal.error.exception.ValidationException
import com.es.segurofinal.models.Role

object Utils {

    fun verificarDatosUsuario(usuarioRegisterDTO: UsuarioRegisterDTO) {
        if (usuarioRegisterDTO.username.isBlank()
            || usuarioRegisterDTO.password.isBlank()
            || usuarioRegisterDTO.passwordRepeat.isBlank()
            || usuarioRegisterDTO.roles == null) {
            throw ValidationException("Uno o mas campos vacíos")
        }

        if (usuarioRegisterDTO.password != usuarioRegisterDTO.passwordRepeat) {
            throw ValidationException("Las contraseñas no son iguales")
        }

    }
}