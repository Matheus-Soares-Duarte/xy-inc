package com.matheussd.xyinc.builders

import com.matheussd.xyinc.domain.PointOfInterest

class PointOfInterestBuilder {
    private var pointOfInterest: PointOfInterest? = null

    fun makePointOfInterest(): PointOfInterestBuilder{
        var pointOfInterestBuider = PointOfInterestBuilder()
        pointOfInterestBuider.pointOfInterest = PointOfInterest("Point Of Interest Name", 10, 10)

        return pointOfInterestBuider
    }

    fun whithNegativeCoordinates(): PointOfInterestBuilder{
        pointOfInterest?.pointOfInterestCompositeId!!.x = -1
        pointOfInterest?.pointOfInterestCompositeId!!.y = -1
        return this
    }

    fun whithBlankName(): PointOfInterestBuilder{
        pointOfInterest?.pointOfInterestCompositeId!!.name = ""
        return this
    }

    fun build(): PointOfInterest {
        return pointOfInterest!!
    }
}