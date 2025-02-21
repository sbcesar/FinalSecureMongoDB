package com.es.segurofinal.error.exception

class ValidationException(message: String) : Exception("Validation error (400) $message") {
}