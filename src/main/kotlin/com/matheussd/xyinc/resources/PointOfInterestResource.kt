package com.matheussd.xyinc.resources

import com.matheussd.xyinc.domain.PointOfInterest
import com.matheussd.xyinc.domain.PointOfInterestCompositeId
import com.matheussd.xyinc.services.PointOfInterestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

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

    @PostMapping
    fun insert(@RequestBody pointOfInterestCompositeId: PointOfInterestCompositeId): ResponseEntity<Void> {

        val pointOfInterest = pointOfInterestService!!.insert(pointOfInterestCompositeId)
        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/find?name={name}&x={x}&y={y}")
                .buildAndExpand(pointOfInterest.pointOfInterestCompositeId.name,
                        pointOfInterest.pointOfInterestCompositeId.x,
                        pointOfInterest.pointOfInterestCompositeId.y)
                .toUri()

        return ResponseEntity.created(uri).build()
    }

}