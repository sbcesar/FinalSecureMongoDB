package com.es.segurofinal.repository

import com.es.segurofinal.models.Tarea
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TareaRepository : MongoRepository<Tarea, String> {

    fun findTareaByTitulo(titulo:String): Optional<Tarea>
}