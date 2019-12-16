package com.matheussd.xyinc.repositories

import com.matheussd.xyinc.domain.PointOfInterest
import com.matheussd.xyinc.domain.PointOfInterestCompositeId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PointOfInterestRepository: CrudRepository<PointOfInterest, PointOfInterestCompositeId>