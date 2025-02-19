package com.es.segurofinal.repository

import com.es.segurofinal.models.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UsuarioRepository : MongoRepository<Usuario, String> {

    fun findByUsername(username: String): Optional<Usuario>
}