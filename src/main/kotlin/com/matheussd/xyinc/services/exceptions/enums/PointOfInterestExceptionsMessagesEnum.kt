package com.matheussd.xyinc.services.exceptions.enums

import com.matheussd.xyinc.domain.PointOfInterest

enum class PointOfInterestExceptionsMessagesEnum {
    BAD_REQUEST_BLANK_NAME(1, ExceptionsMessagesEnum.BAD_REQUEST.message +
            " The name value is blank!"),
    BAD_REQUEST_NEGATIVE_VALUE(2, ExceptionsMessagesEnum.BAD_REQUEST.message +
            " There are negative values!"),
    BAD_REQUEST_NON_INTEGER_VALUE(3, ExceptionsMessagesEnum.BAD_REQUEST.message +
            " There are non-integer values!"),

    OBJECT_NOT_FOUND_EMPTY_DATEBASE(4, ExceptionsMessagesEnum.OBJECT_NOT_FOUND.message +
            " There are no elements of type "+ PointOfInterest::class.simpleName+" in the database."),
    OBJECT_NOT_FOUND_FOR_REQUEST(5, ExceptionsMessagesEnum.OBJECT_NOT_FOUND.message +
            " Type Element: "+ PointOfInterest::class.simpleName+".");


    val code: Int
    val message: String

    private constructor(code: Int, message: String){
        this.code    = code
        this.message = message
    }

    fun toEnum(code: Int): PointOfInterestExceptionsMessagesEnum {

        for(messageEnum in PointOfInterestExceptionsMessagesEnum.values()){
            if( code == messageEnum.code )
                return messageEnum
        }

        throw IllegalArgumentException("Invalid Code: $code!")
    }

}