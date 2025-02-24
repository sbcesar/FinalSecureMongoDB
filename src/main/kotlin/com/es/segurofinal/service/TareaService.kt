package com.es.segurofinal.service

import com.es.segurofinal.models.Tarea
import com.es.segurofinal.repository.TareaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TareaService {

    @Autowired
    private lateinit var tareaRepository: TareaRepository

    fun seeAllTasks(): List<Tarea> {
        return tareaRepository.findAll()
    }
}