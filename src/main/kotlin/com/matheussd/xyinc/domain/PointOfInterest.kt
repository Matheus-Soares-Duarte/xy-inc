package com.matheussd.xyinc.domain

import java.io.Serializable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "point_of_interest")
class PointOfInterest: Serializable {

    @EmbeddedId
    var pointOfInterestCompositeId: PointOfInterestCompositeId

    constructor(name: String, x: Int, y: Int){
        this.pointOfInterestCompositeId = PointOfInterestCompositeId(name, x, y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointOfInterest

        if (pointOfInterestCompositeId != other.pointOfInterestCompositeId) return false

        return true
    }

    override fun hashCode(): Int {
        return pointOfInterestCompositeId.hashCode()
    }

    override fun toString(): String {
        return "PointOfInterest $pointOfInterestCompositeId"
    }

}