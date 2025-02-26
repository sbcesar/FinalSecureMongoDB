package com.es.segurofinal.error.exception

class NotImplementedError(message: String) : Error("The server does not support the functionality required to fulfill the request (501): $message") {
}