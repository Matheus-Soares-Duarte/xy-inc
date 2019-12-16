package com.matheussd.xyinc.services.exceptions

class BadRequestException: RuntimeException {

    constructor(message: String): super(message)

    constructor(message: String, cause: Throwable): super(message, cause)

}