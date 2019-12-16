package com.matheussd.xyinc.services.exceptions.enums

enum class ExceptionsMessagesEnum {
    BAD_REQUEST(1, "Bad Request! Please, make sure all required values are entered and if then are the right type!"),
    OBJECT_NOT_FOUND(2, "Object Not Found! There is no elements matching the request!"),
    UPGRADE_REQUIRED(3, "Upgrade Required! The required element cannot be created as these values already exist!");

    val code: Int
    val message: String

    private constructor(code: Int, message: String){
        this.code    = code
        this.message = message
    }

    fun toEnum(code: Int): ExceptionsMessagesEnum {

        for(messageEnum in ExceptionsMessagesEnum.values()){
            if( code == messageEnum.code )
                return messageEnum
        }

        throw IllegalArgumentException("Invalid Code: $code!")
    }

}