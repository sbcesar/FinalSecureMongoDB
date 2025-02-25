package com.es.segurofinal.service

import com.es.segurofinal.error.exception.ForbiddenException
import com.es.segurofinal.error.exception.NotFoundException
import com.es.segurofinal.models.Tarea
import com.es.segurofinal.repository.TareaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    fun seeAllTasks(): List<Tarea> {
        return tareaRepository.findAll()
    }

    fun showTaskById(id: String): Tarea {

    }

    fun createTask(tarea: Tarea): Tarea {

    }

    fun updateTask(id: String, tarea: Tarea): Tarea {
        // Comprobamos si la tarea existe
        val tareaExistente = tareaRepository.findById(id).orElseThrow {
            NotFoundException("Task not found")
        }

        // Comprobamos que la tarea sea del usuario
        if (!tareaExistente.usuario.username.equals(authentication.name)) {
            throw ForbiddenException("You are not allowed to update this task")
        }
    }

    fun completeTask(id: String): Tarea {

    }

    fun deleteTask(id: String): Tarea {

    }
}