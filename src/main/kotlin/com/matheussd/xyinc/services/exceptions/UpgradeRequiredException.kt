package com.matheussd.xyinc.services.exceptions

class UpgradeRequiredException: RuntimeException {

    constructor(message: String): super(message)

    constructor(message: String, cause: Throwable): super(message, cause)

}