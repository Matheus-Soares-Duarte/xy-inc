package com.matheussd.xyinc.builders

import com.matheussd.xyinc.domain.PointOfInterest

class PointOfInterestBuilder {
    private lateinit var pointOfInterest: PointOfInterest

    companion object {
        fun makePointOfInterest(): PointOfInterestBuilder{
            var pointOfInterestBuilder = PointOfInterestBuilder()
            pointOfInterestBuilder.pointOfInterest = PointOfInterest("Point Of Interest Name", 10, 10)

            return pointOfInterestBuilder
        }
    }

    fun whithNegativeCoordinates(): PointOfInterestBuilder{
        pointOfInterest.pointOfInterestCompositeId.x = -1
        pointOfInterest.pointOfInterestCompositeId.y = -1
        return this
    }

    fun whithBlankName(): PointOfInterestBuilder{
        pointOfInterest.pointOfInterestCompositeId.name = ""
        return this
    }

    fun build(): PointOfInterest {
        return pointOfInterest
    }
}