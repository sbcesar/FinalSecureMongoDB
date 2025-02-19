package com.es.segurofinal.models

data class Usuario(
    val username: String,
    val password: String,
    val role: Role
)