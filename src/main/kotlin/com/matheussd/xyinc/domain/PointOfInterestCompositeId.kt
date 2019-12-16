package com.matheussd.xyinc.domain

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class PointOfInterestCompositeId: Serializable {

    var name: String
    var x: Int
    var y: Int

    constructor(name: String, x: Int, y: Int){
        this.name = name.toUpperCase()
        this.x = x
        this.y = y
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointOfInterestCompositeId

        if (name != other.name) return false
        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "[Name: $name, Coordinates: ($x, $y)]"
    }
}