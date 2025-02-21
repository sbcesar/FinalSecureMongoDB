package com.es.segurofinal.controller

import com.es.segurofinal.dto.UsuarioDTO
import com.es.segurofinal.dto.UsuarioLoginDTO
import com.es.segurofinal.dto.UsuarioRegisterDTO
import com.es.segurofinal.error.exception.UnauthorizedException
import com.es.segurofinal.service.TokenService
import com.es.segurofinal.service.UsuarioService
import com.es.segurofinal.utils.Utils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping("/register")
    fun register(
        httpRequest: HttpServletRequest,
        @RequestBody usuarioRegisterDTO: UsuarioRegisterDTO
    ) : ResponseEntity<UsuarioDTO>? {

        Utils.verificarDatosUsuario(usuarioRegisterDTO)

        val nuevousuario = usuarioService.insertUser(usuarioRegisterDTO)
        return if (nuevousuario == null) {
            ResponseEntity(null, HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(null, HttpStatus.CREATED)
        }
    }

    @PostMapping("/login")
    fun login(
        @RequestBody usuario: UsuarioLoginDTO
    ) : ResponseEntity<Any>? {

        val autentication: Authentication

        try {
            autentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.username, usuario.password))
        } catch (e: AuthenticationException) {
            throw UnauthorizedException("Credenciales incorrectas -> ${e.message}")
        }

        val token = tokenService.generarToken(autentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }
}