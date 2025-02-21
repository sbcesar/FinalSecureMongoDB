package com.es.segurofinal.repository

import com.es.segurofinal.models.Usuario

interface UsuarioCustomRepository {
    fun findByCiudad(ciudad: String): List<Usuario>

    fun findByMunicipio(municipio: String): List<Usuario>
}