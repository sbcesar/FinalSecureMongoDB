package com.es.segurofinal.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.github.cdimascio.dotenv.dotenv

class DatabaseManager {
    private val dbName = "cserben"
    private val dotenv = dotenv()
    private val connectionString = dotenv["URL_MONGODB"]
    private var mongoClient: MongoClient? = null
    private var database: MongoDatabase? = null

    companion object {
        private var instance: DatabaseManager? = null

        fun getInstance(): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager()
            }
            return instance!!
        }
    }

    fun connect(): MongoDatabase {
        if (mongoClient == null || database == null) {
            try {
                mongoClient = MongoClients.create(connectionString)
                database = mongoClient?.getDatabase(dbName)
                println("Conexión a la base de datos establecida")
            } catch (e: Exception) {
                println("Error al conectar con la base de datos: ${e.message}")
                throw e
            }
        }
        return database!!
    }

    fun disconnect() {
        try {
            mongoClient?.close()
        }catch (e:Exception){
            println(e.message)
        }
    }
}