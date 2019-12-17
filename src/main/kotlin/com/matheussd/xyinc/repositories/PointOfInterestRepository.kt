package com.matheussd.xyinc.repositories

import com.matheussd.xyinc.domain.PointOfInterest
import com.matheussd.xyinc.domain.PointOfInterestCompositeId
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PointOfInterestRepository: CrudRepository<PointOfInterest, PointOfInterestCompositeId>{

    @Query(value = "select * from point_of_interest p where ceil(sqrt( ((p.x - :x) ^ 2) + ((p.y - :y) ^ 2) ) ) <= :maxDistance", nativeQuery = true)
    fun findForProximity(@Param("x") x: Int, @Param("y")y: Int, @Param("maxDistance")maxDistance: Int): List<PointOfInterest>

}