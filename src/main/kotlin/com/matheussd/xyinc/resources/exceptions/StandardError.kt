package com.matheussd.xyinc.resources.exceptions

import java.io.Serializable
import java.time.LocalDateTime

class StandardError(var message: String, var status: Int): Serializable {

    var timestampe: LocalDateTime

    init {
        this.timestampe = LocalDateTime.now()
    }
}