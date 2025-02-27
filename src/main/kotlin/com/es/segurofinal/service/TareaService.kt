package com.es.segurofinal.service

import com.es.segurofinal.dto.TareaDTO
import com.es.segurofinal.dto.UsuarioDTO
import com.es.segurofinal.error.exception.ConflictException
import com.es.segurofinal.error.exception.ForbiddenException
import com.es.segurofinal.error.exception.NotFoundException
import com.es.segurofinal.models.Estado
import com.es.segurofinal.models.Role
import com.es.segurofinal.models.Tarea
import com.es.segurofinal.repository.TareaRepository
import com.es.segurofinal.repository.UsuarioRepository
import com.es.segurofinal.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TareaService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    fun seeAllTasks(): List<Tarea> {
        return tareaRepository.findAll()
    }

    fun getMyTasks(usuarioLogueado: UsuarioDTO): List<Tarea> {
        val usuario = usuarioRepository.findByUsername(usuarioLogueado.username).orElseThrow {
            NotFoundException("Usuario not found")
        }
        return tareaRepository.findByUsuario(usuario).orElse(emptyList())
    }

    fun createTask(tareaDTO: TareaDTO, usuarioDTO: UsuarioDTO): Tarea {

        // Buscamos el usuario en la db y lo extraemos
        val usuarioAsignado = if (usuarioDTO.roles == Role.ADMIN) {
            usuarioRepository.findById(tareaDTO.usuarioId).orElseThrow {
                NotFoundException("Usuario con ID ${tareaDTO.usuarioId} not found")
            }
        } else {
            usuarioRepository.findByUsername(usuarioDTO.username)
                .orElseThrow { NotFoundException("Usuario no encontrado") }
        }

        if (usuarioAsignado == null) throw NotFoundException("Usuario no encontrado porque es nulo")

        // Verificar los datos de la tarea
        Utils.verificarDatosTarea(tareaDTO, usuarioAsignado)

        // Conversion de TareaDTO a Tarea para implementarla en la bd
        val tarea = Tarea(
            _id = null,
            titulo = tareaDTO.titulo,
            descripcion = tareaDTO.descripcion,
            estado = tareaDTO.estado,
            usuario = usuarioAsignado
        )

        return tareaRepository.save(tarea)
    }

    fun updateTask(usuarioLogueado: UsuarioDTO, id: String, tareaDTO: TareaDTO): Tarea {
        // Comprobamos si la tarea existe
        val tareaExistente = tareaRepository.findById(id).orElseThrow {
            NotFoundException("Task not found")
        }

        // Comprobamos que la tarea sea del usuario
        if (tareaExistente.usuario.username != usuarioLogueado.username) {
            throw ForbiddenException("You are not allowed to update this task")
        }

        // Buscamos el usuario en la db y lo extraemos
        val usuario = usuarioRepository.findByUsername(usuarioLogueado.username)
            .orElseThrow { NotFoundException("Usuario no encontrado") }

        // Verificamos los datos de la nueva tarea
        Utils.verificarDatosTarea(tareaDTO, usuario)

        // Convertimos la tareadto a la tarea (usamos el usuario de la tarea que ya esta en la bd)
        val tarea = Tarea(
            _id = null,
            titulo = tareaDTO.titulo,
            descripcion = tareaDTO.descripcion,
            estado = tareaDTO.estado,
            usuario = tareaExistente.usuario
        )

        // Actualizamos la traea (si no se aporta el dato usa el de la tarea existente)
        val nuevaTarea = tareaExistente.copy(
            _id = tarea._id ?: tareaExistente._id,
            titulo = tarea.titulo ?: tareaExistente.titulo,
            descripcion = tarea.descripcion ?: tareaExistente.descripcion,
            estado = tarea.estado ?: tareaExistente.estado,
            usuario = tarea.usuario ?: tareaExistente.usuario
        )

        tareaRepository.save(nuevaTarea)

        return nuevaTarea
    }

    fun completeTask(usuarioLogueado: UsuarioDTO, id: String): Tarea {
        // Comprobamos si la tarea existe
        val tareaExistente = tareaRepository.findById(id).orElseThrow {
            NotFoundException("Task not found")
        }

        // Si el usuario no es ADMIN, verificamos que sea el dueño de la tarea
        if (usuarioLogueado.roles != Role.ADMIN && tareaExistente.usuario.username != usuarioLogueado.username) {
            throw ForbiddenException("You are not allowed to update this task")
        }

        // Cambio de estado de la tarea
        if (tareaExistente.estado == Estado.COMPLETADO) throw ConflictException("La tarea ya estaba completada")

        tareaExistente.estado = Estado.COMPLETADO

        tareaRepository.save(tareaExistente)

        return tareaExistente
    }

    fun deleteTask(usuarioLogueado: UsuarioDTO, id: String) {
        // Comprobamos si la tarea existe
        val tareaExistente = tareaRepository.findById(id).orElseThrow {
            NotFoundException("Task not found")
        }

        // Si el usuario no es ADMIN, verificamos que sea el dueño de la tarea
        if (usuarioLogueado.roles != Role.ADMIN && tareaExistente.usuario.username != usuarioLogueado.username) {
            throw ForbiddenException("You are not allowed to delete this task")
        }

        tareaRepository.deleteById(id)
    }
}