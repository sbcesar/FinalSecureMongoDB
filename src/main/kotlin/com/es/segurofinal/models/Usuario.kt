package com.es.segurofinal.models

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document

@Document("Usuario")
data class Usuario(
    @BsonId
    val _id: String?,
    val username: String,
    val password: String,
    val roles: Role? = Role.USER,
    val direccion: Direccion
)