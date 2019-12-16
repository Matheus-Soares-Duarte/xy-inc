package com.matheussd.xyinc.resources

import com.matheussd.xyinc.domain.PointOfInterest
import com.matheussd.xyinc.services.PointOfInterestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/poi"])
class PointOfInterestResource {

    @Autowired
    val pointOfInterestService: PointOfInterestService? = null

    @GetMapping(value = ["/find"])
    fun find( @RequestParam(value = "name") name: String,
              @RequestParam(value = "x") x: String,
              @RequestParam(value = "y") y: String
    ): ResponseEntity<PointOfInterest> {

        val pointOfInterest = pointOfInterestService!!.find(name, x, y)
        return ResponseEntity.ok().body(pointOfInterest)
    }


}