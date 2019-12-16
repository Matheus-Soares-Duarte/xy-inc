package com.matheussd.xyinc.services

import com.matheussd.xyinc.domain.PointOfInterest
import com.matheussd.xyinc.domain.PointOfInterestCompositeId
import com.matheussd.xyinc.repositories.PointOfInterestRepository
import com.matheussd.xyinc.services.exceptions.BadRequestException
import com.matheussd.xyinc.services.exceptions.ObjectNotFoundException
import com.matheussd.xyinc.services.exceptions.UpgradeRequiredException
import com.matheussd.xyinc.services.exceptions.enums.ExceptionsMessagesEnum
import com.matheussd.xyinc.services.exceptions.enums.PointOfInterestExceptionsMessagesEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.NumberFormatException

@Service
class PointOfInterestService {

    @Autowired
    var pointOfInterestRepository: PointOfInterestRepository? = null

    private fun returnValidPointOfInterestNameOf(name: String): String {
        if(name.isBlank()){
            throw BadRequestException(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_BLANK_NAME.message)
        }

        return name
    }

    private fun returnValidPointOfInterestNumberOf(number: String): Int {
        val validatedNumber: Int

        try {
            validatedNumber = number.toInt()

            if(validatedNumber<0){
                throw BadRequestException(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NEGATIVE_VALUE.message +
                        " Invalid Value: $number.")
            }

            return validatedNumber
        } catch (exception: NumberFormatException){
            throw BadRequestException(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NON_INTEGER_VALUE.message +
                    " Invalid Value: $number.")
        }
    }

    private fun returnValidPointOfInterestCompositeId(name: String, x: String, y: String): PointOfInterestCompositeId {
        val validatedName = returnValidPointOfInterestNameOf(name)
        val validatedX    = returnValidPointOfInterestNumberOf(x)
        val validatedY    = returnValidPointOfInterestNumberOf(y)

        return PointOfInterestCompositeId(validatedName, validatedX, validatedY)
    }

    fun find(name: String, x: String, y: String): PointOfInterest {
        val pointOfInterestCompositeId = returnValidPointOfInterestCompositeId(name, x, y)
        val pointOfInterest = pointOfInterestRepository!!.findById( pointOfInterestCompositeId )

        return pointOfInterest.orElseThrow {
            throw ObjectNotFoundException(PointOfInterestExceptionsMessagesEnum.OBJECT_NOT_FOUND_FOR_REQUEST.message +
                    " Requested Element: $pointOfInterestCompositeId.")
        }
    }

    fun insert(pointOfInterestCompositeId: PointOfInterestCompositeId): PointOfInterest {
        try {
            val pointOfInterest = find(pointOfInterestCompositeId.name,
                    pointOfInterestCompositeId.x.toString(),
                    pointOfInterestCompositeId.y.toString())

            throw UpgradeRequiredException (ExceptionsMessagesEnum.UPGRADE_REQUIRED.message +
                    " Requested Element: $pointOfInterest.")
        } catch (exception: ObjectNotFoundException){
            val pointOfInterest = PointOfInterest(pointOfInterestCompositeId.name, pointOfInterestCompositeId.x, pointOfInterestCompositeId.y)
            return pointOfInterestRepository!!.save(pointOfInterest)
        }
    }
}