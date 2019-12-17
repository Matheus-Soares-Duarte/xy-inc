package com.matheussd.xyinc.resources

import com.matheussd.xyinc.domain.PointOfInterest
import com.matheussd.xyinc.services.PointOfInterestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping(value = ["/poi"])
class PointOfInterestResource(private val pointOfInterestService: PointOfInterestService) {

    @GetMapping(value = ["/find"])
    fun find( @RequestParam(value = "name") name: String,
              @RequestParam(value = "x") x: String,
              @RequestParam(value = "y") y: String
    ): ResponseEntity<PointOfInterest> {

        val pointOfInterest = pointOfInterestService.find(name, x, y)
        return ResponseEntity.ok().body(pointOfInterest)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<PointOfInterest>> {

        val pointOfInterestList = pointOfInterestService.findAll()
        return ResponseEntity.ok().body(pointOfInterestList)
    }

    @GetMapping(value = ["/findForProximity"])
    fun findForProximity(
            @RequestParam(value = "x") x: String,
            @RequestParam(value = "y") y: String,
            @RequestParam(value = "maxDistance") maxDistance: String
    ): ResponseEntity< List<PointOfInterest> > {

        val pointOfInterestList = pointOfInterestService.findForProximity(x, y, maxDistance)
        return ResponseEntity.ok().body(pointOfInterestList)
    }

    @PostMapping
    fun insert(@RequestBody pointOfInterest: PointOfInterest): ResponseEntity<Void> {

        val responsePointOfInterest = pointOfInterestService.insert( pointOfInterest )
        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/find?name={name}&x={x}&y={y}")
                .buildAndExpand(responsePointOfInterest.pointOfInterestCompositeId.name,
                        responsePointOfInterest.pointOfInterestCompositeId.x,
                        responsePointOfInterest.pointOfInterestCompositeId.y)
                .toUri()

        return ResponseEntity.created(uri).build()
    }

}