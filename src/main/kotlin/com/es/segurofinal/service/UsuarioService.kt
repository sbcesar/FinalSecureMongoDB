package com.es.segurofinal.service

import com.es.segurofinal.dto.UsuarioDTO
import com.es.segurofinal.dto.UsuarioRegisterDTO
import com.es.segurofinal.error.exception.NotFoundException
import com.es.segurofinal.error.exception.UnauthorizedException
import com.es.segurofinal.models.Usuario
import com.es.segurofinal.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var externalApiService: ExternalApiService

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow {
                NotFoundException("Usuario $username not found")
            }

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles?.name)
            .build()
    }

    fun findByUsername(username: String): UsuarioDTO? {
        val usuario = usuarioRepository.findByUsername(username).orElse(null) ?: return null

        val usuarioDTO = UsuarioDTO(
            username = usuario.username,
            roles = usuario.roles,
            direccion = usuario.direccion
        )

        return usuarioDTO
    }

    fun insertUser(usuarioRegisterDTO: UsuarioRegisterDTO): UsuarioDTO? {

        val usuarioFound = usuarioRepository.findByUsername(usuarioRegisterDTO.username)

        if (usuarioFound.isPresent) {
            return null
        } else {

            val datosProvincias = externalApiService.obtenerProvinciasDesdeApi()
            var cpro = ""
            if(datosProvincias != null) {
                if(datosProvincias.data != null) {
                    val provinciaEncontrada = datosProvincias.data.stream().filter {
                        it.PRO == usuarioRegisterDTO.direccion.provincia.uppercase()
                    }.findFirst().orElseThrow {
                        NotFoundException("Provincia ${usuarioRegisterDTO.direccion.provincia} no encontrada")
                    }
                    cpro = provinciaEncontrada.CPRO
                }
            }

            val datosMunicipios = externalApiService.obtenerMunicipiosDesdeApi(cpro)
            if(datosMunicipios != null) {
                if(datosMunicipios.data != null) {
                    datosMunicipios.data.stream().filter {
                        it.DMUN50 == usuarioRegisterDTO.direccion.municipio.uppercase()
                    }.findFirst().orElseThrow {
                        NotFoundException("Municipio ${usuarioRegisterDTO.direccion.municipio} incorrecto")
                    }
                }
            }


            val usuarioDTO = UsuarioDTO(
                username = usuarioRegisterDTO.username,
                roles = usuarioRegisterDTO.roles,
                direccion = usuarioRegisterDTO.direccion
            )

            val usuario = Usuario(
                _id = null,
                username = usuarioRegisterDTO.username,
                password = passwordEncoder.encode(usuarioRegisterDTO.password),
                roles = usuarioRegisterDTO.roles,
                direccion = usuarioRegisterDTO.direccion
            )

            usuarioRepository.insert(usuario)

            return usuarioDTO
        }
    }
}