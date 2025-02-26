package com.es.segurofinal.error

import com.es.segurofinal.error.exception.ConflictException
import com.es.segurofinal.error.exception.UnauthorizedException
import com.es.segurofinal.error.exception.ValidationException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.naming.AuthenticationException

@ControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(AuthenticationException::class, UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleAuthentication(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(Exception::class, NullPointerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handleGeneric(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        e.printStackTrace()
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleValidationException(request: HttpServletRequest, e: ValidationException): ErrorRespuesta {
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(NotImplementedError::class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    fun handleNotImplementedError(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    fun handleConflictException(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

}