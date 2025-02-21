package com.es.segurofinal.repository.impl

import com.es.segurofinal.models.Usuario
import com.es.segurofinal.repository.UsuarioCustomRepository
import com.mongodb.client.model.Filters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class UsuarioCustomRepositoryImpl: UsuarioCustomRepository {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun findByCiudad(ciudad: String): List<Usuario> {
        val database = mongoTemplate.db
        val collection = database.getCollection("collUsuarios", Usuario::class.java)

        val filtroCiudad = Filters.eq("direccion.ciudad", ciudad)
        val usuarios = collection.find(filtroCiudad).toList()

        return usuarios
    }

    override fun findByMunicipio(municipio: String): List<Usuario> {
        val database = mongoTemplate.db
        val collection = database.getCollection("collUsuarios", Usuario::class.java)

        val filtroMunicipio = Filters.eq("direccion.municipio", municipio)
        val usuarios = collection.find(filtroMunicipio).toList()

        return usuarios
    }


}