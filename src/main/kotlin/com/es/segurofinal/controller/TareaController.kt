package com.es.segurofinal.controller

import com.es.segurofinal.models.Tarea
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tareas")
class TareaController {

    @GetMapping("/show")
    fun seeAllTasks(): ResponseEntity<List<Tarea>> {

    }

    @GetMapping("/show/{id}")
    fun showTaskById(
        @PathVariable("id") id: String?
    ): ResponseEntity<Tarea> {

    }

    @PostMapping("/create")
    fun createTask(
        @RequestBody tarea: Tarea?
    ): ResponseEntity<Tarea> {

    }

    @PutMapping("/update/{id}")
    fun updateTask(
        @PathVariable("id") id: String?,
        @RequestBody tarea: Tarea?
    ): ResponseEntity<Tarea> {

    }

    @PutMapping("/complete/{id}")
    fun completeTask(
        @PathVariable("id") id: String?,
        @RequestBody tarea: Tarea?
    ): ResponseEntity<Tarea> {

    }

    @DeleteMapping("/delete/{id}")
    fun deleteTask(
        @PathVariable("id") id: String?
    ): ResponseEntity<Tarea?> {

    }
}