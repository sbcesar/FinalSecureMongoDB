package com.es.segurofinal.controller

import com.es.segurofinal.dto.TareaDTO
import com.es.segurofinal.models.Tarea
import com.es.segurofinal.service.TareaService
import com.es.segurofinal.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tareas")
class TareaController {

    @Autowired
    private lateinit var tareaService: TareaService
    @Autowired
    private lateinit var usuarioService: UsuarioService

    @GetMapping("/show")
    fun seeAllTasks(): ResponseEntity<List<Tarea>> {
        val listaTareas = tareaService.seeAllTasks()

        return if (listaTareas.isNotEmpty()) {
            ResponseEntity(listaTareas, HttpStatus.OK)
        } else {
            ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }

    }

    @GetMapping("/showTask")
    fun getMyTasks(authentication: Authentication): ResponseEntity<List<Tarea>> {
        val username = authentication.name
        val usuario = usuarioService.findByUsername(username) ?: return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val tareas = tareaService.getMyTasks(usuario)
        return ResponseEntity(tareas, HttpStatus.OK)
    }

    @PostMapping("/create")
    fun createTask(
        @RequestBody tareaDTO: TareaDTO?,
        authentication: Authentication
    ): ResponseEntity<Tarea> {
        if (tareaDTO == null) {
            throw NotImplementedError("Body tarea is required")
        }

        val username = authentication.name
        val usuario = usuarioService.findByUsername(username) ?: return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val tarea = tareaService.createTask(tareaDTO, usuario)
        return ResponseEntity(tarea, HttpStatus.CREATED)
    }

    @PutMapping("/update/{id}")
    fun updateTask(
        authentication: Authentication,
        @PathVariable("id") id: String?,
        @RequestBody tarea: TareaDTO?
    ): ResponseEntity<Tarea> {
        if (id.isNullOrBlank()) throw NotImplementedError("Id is required")

        if (tarea == null) throw NotImplementedError("Tarea is required")

        // Con autenticacion compruebo si el rol es tal y doy permisos de modificación

        val username = authentication.name
        val usuario = usuarioService.findByUsername(username) ?: return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val nuevaTarea = tareaService.updateTask(usuario, id, tarea)
        return ResponseEntity(nuevaTarea, HttpStatus.OK)
    }

    @PutMapping("/complete/{id}")
    fun completeTask(
        authentication: Authentication,
        @PathVariable("id") id: String?
    ): ResponseEntity<Tarea> {
        if (id.isNullOrBlank()) throw NotImplementedError("Id is required")

        val username = authentication.name
        val usuario = usuarioService.findByUsername(username) ?: return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val tareaModificada = tareaService.completeTask(usuario, id)
        return ResponseEntity(tareaModificada, HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteTask(
        authentication: Authentication,
        @PathVariable("id") id: String?
    ): ResponseEntity<Tarea?> {
        if (id.isNullOrBlank()) throw NotImplementedError("Id is required")

        val username = authentication.name
        val usuario = usuarioService.findByUsername(username) ?: return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        tareaService.deleteTask(usuario, id)

        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }
}