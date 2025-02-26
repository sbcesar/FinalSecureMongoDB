package com.es.segurofinal.utils

import com.es.segurofinal.dto.TareaDTO
import com.es.segurofinal.dto.UsuarioRegisterDTO
import com.es.segurofinal.error.exception.NotFoundException
import com.es.segurofinal.error.exception.ValidationException
import com.es.segurofinal.models.Usuario

object Utils {

    fun verificarDatosUsuarioRegisterDTO(usuarioRegisterDTO: UsuarioRegisterDTO) {
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

    fun verificarDatosUsuario(usuario: Usuario) {
        if (usuario.username.isBlank()
            || usuario.password.isBlank()
            || usuario.roles == null) {
            throw ValidationException("Uno o mas campos de usuario vacíos")
        }
    }

    fun verificarDatosTarea(tarea: TareaDTO, usuario: Usuario?) {
        if (tarea.titulo.isBlank()
            || tarea.descripcion.isBlank()) {
            throw ValidationException("Uno o mas campos de tarea vacíos")
        }

        if (usuario == null) {
            throw ValidationException("Usuario no encontrado")
        }

        verificarDatosUsuario(usuario)
    }
}