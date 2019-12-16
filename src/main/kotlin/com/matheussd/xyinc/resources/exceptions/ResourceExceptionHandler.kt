package com.matheussd.xyinc.resources.exceptions

import com.matheussd.xyinc.services.exceptions.BadRequestException
import com.matheussd.xyinc.services.exceptions.ObjectNotFoundException
import com.matheussd.xyinc.services.exceptions.UpgradeRequiredException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ResourceExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun badRequest(exception: BadRequestException, request: HttpServletRequest): ResponseEntity<StandardError>{
        val error = StandardError(exception.message!!, HttpStatus.BAD_REQUEST.value())
        return ResponseEntity.status( HttpStatus.BAD_REQUEST.value() ).body( error )
    }

    @ExceptionHandler(ObjectNotFoundException::class)
    fun objectNotFound(exception: ObjectNotFoundException, request: HttpServletRequest): ResponseEntity<StandardError>{
        val error = StandardError(exception.message!!, HttpStatus.NOT_FOUND.value())
        return ResponseEntity.status( HttpStatus.NOT_FOUND.value() ).body( error )
    }

    @ExceptionHandler(UpgradeRequiredException::class)
    fun upgradeRequired(exception: UpgradeRequiredException, request: HttpServletRequest): ResponseEntity<StandardError>{
        val error = StandardError(exception.message!!, HttpStatus.UPGRADE_REQUIRED.value())
        return ResponseEntity.status( HttpStatus.UPGRADE_REQUIRED.value() ).body( error )
    }

}