package com.matheussd.xyinc.domain

import java.io.Serializable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "point_of_interest")
class PointOfInterest(name: String, x: Int, y: Int) : Serializable {

    @EmbeddedId
    var pointOfInterestCompositeId: PointOfInterestCompositeId = PointOfInterestCompositeId(name, x, y)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointOfInterest

        if ( pointOfInterestCompositeId != other.pointOfInterestCompositeId ) return false

        return true
    }

    override fun hashCode(): Int {
        return pointOfInterestCompositeId.hashCode()
    }

    override fun toString(): String {
        return "PointOfInterest $pointOfInterestCompositeId"
    }

}