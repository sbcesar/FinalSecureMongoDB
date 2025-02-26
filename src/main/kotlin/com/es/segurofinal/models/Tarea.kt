package com.es.segurofinal.models

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document

@Document("Tareas")
data class Tarea(
    @BsonId
    val _id: String?,
    val titulo: String,
    val descripcion: String,
    var estado: Estado = Estado.PENDIENTE,
    val usuario: Usuario
)