package com.matheussd.xyinc.services

import com.matheussd.xyinc.builders.PointOfInterestBuilder
import com.matheussd.xyinc.domain.PointOfInterest
import com.matheussd.xyinc.repositories.PointOfInterestRepository
import com.matheussd.xyinc.services.exceptions.BadRequestException
import com.matheussd.xyinc.services.exceptions.ObjectNotFoundException
import com.matheussd.xyinc.services.exceptions.UpgradeRequiredException
import com.matheussd.xyinc.services.exceptions.enums.ExceptionsMessagesEnum
import com.matheussd.xyinc.services.exceptions.enums.PointOfInterestExceptionsMessagesEnum
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mockito
import java.util.*

@RunWith(BlockJUnit4ClassRunner::class)
class PointOfInterestServiceTest {

    private val mockPointOfInterestRepository = Mockito.mock(PointOfInterestRepository::class.java)

    private var pointOfInterestService = PointOfInterestService(mockPointOfInterestRepository)

    @Test
    fun `function find should find point of interest` () {
        val builtPointOfInterest = PointOfInterestBuilder.makePointOfInterest().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterest.pointOfInterestCompositeId))
                .thenReturn( Optional.of(builtPointOfInterest) )

        try {
            val returnedPointOfInterest = pointOfInterestService.find(
                    builtPointOfInterest.pointOfInterestCompositeId.name,
                    builtPointOfInterest.pointOfInterestCompositeId.x.toString(),
                    builtPointOfInterest.pointOfInterestCompositeId.y.toString()
            )

            Assert.assertEquals(builtPointOfInterest, returnedPointOfInterest)
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function find should not find point of interest when not found requested element` () {
        val builtPointOfInterestRequestedElement = PointOfInterestBuilder.makePointOfInterest().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterestRequestedElement.pointOfInterestCompositeId))
                .thenReturn(Optional.empty())

        try {
            pointOfInterestService.find(
                    builtPointOfInterestRequestedElement.pointOfInterestCompositeId.name,
                    builtPointOfInterestRequestedElement.pointOfInterestCompositeId.x.toString(),
                    builtPointOfInterestRequestedElement.pointOfInterestCompositeId.y.toString()
            )

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.OBJECT_NOT_FOUND_FOR_REQUEST.message)
        } catch (exception: ObjectNotFoundException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.OBJECT_NOT_FOUND_FOR_REQUEST.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function find should not find point of interest when the entered coordinates have negative values` () {
        val builtPointOfInterestWithNegativeCoordinates = PointOfInterestBuilder.makePointOfInterest().whithNegativeCoordinates().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId))
                .thenReturn( Optional.of(builtPointOfInterestWithNegativeCoordinates) )

        try {
            pointOfInterestService.find(
                    builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId.name,
                    builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId.x.toString(),
                    builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId.y.toString()
            )

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NEGATIVE_VALUE.message)
        } catch (exception: BadRequestException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NEGATIVE_VALUE.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function find should not find point of interest when the entered coordinates have non integer values` () {
        val nonIntegerCoordinate = "string"
        val builtPointOfInterest  = PointOfInterestBuilder.makePointOfInterest().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterest.pointOfInterestCompositeId))
                .thenReturn(Optional.of(builtPointOfInterest))

        try {
            pointOfInterestService.find(
                    builtPointOfInterest.pointOfInterestCompositeId.name,
                    nonIntegerCoordinate,
                    nonIntegerCoordinate
            )

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NON_INTEGER_VALUE.message)
        } catch (exception: BadRequestException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NON_INTEGER_VALUE.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function find should not find point of interest when the entered name is blank` () {
        val builtPointOfInterestWithBlankName = PointOfInterestBuilder.makePointOfInterest().whithBlankName().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterestWithBlankName.pointOfInterestCompositeId))
                .thenReturn( Optional.of(builtPointOfInterestWithBlankName ))

        try {
            pointOfInterestService.find(
                    builtPointOfInterestWithBlankName.pointOfInterestCompositeId.name,
                    builtPointOfInterestWithBlankName.pointOfInterestCompositeId.x.toString(),
                    builtPointOfInterestWithBlankName.pointOfInterestCompositeId.y.toString()
            )

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_BLANK_NAME.message)
        } catch (exception: BadRequestException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_BLANK_NAME.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function findAll should find all point of interest in database` () {
        val builtPointOfInterest = PointOfInterestBuilder.makePointOfInterest().build()
        val builtOtherPointOfInterest = PointOfInterestBuilder.makePointOfInterest().makeOtherPointOfInterest().build()
        val pointOfInterestList = ArrayList<PointOfInterest>()
        pointOfInterestList.add( builtPointOfInterest )
        pointOfInterestList.add( builtOtherPointOfInterest )

        Mockito.`when`(mockPointOfInterestRepository.findAll())
                .thenReturn( pointOfInterestList.asIterable() )

        try {
            val returnedPointOfInterestList = pointOfInterestService.findAll()

            Assert.assertEquals(pointOfInterestList, returnedPointOfInterestList)
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function findAll should not find point of interest when database is empty` () {
        val emptyPointOfInterestList = ArrayList<PointOfInterest>()

        Mockito.`when`(mockPointOfInterestRepository.findAll())
                .thenReturn( emptyPointOfInterestList.asIterable() )

        try {
            pointOfInterestService.findAll()

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.OBJECT_NOT_FOUND_EMPTY_DATEBASE.message)
        } catch (exception: ObjectNotFoundException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.OBJECT_NOT_FOUND_EMPTY_DATEBASE.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function findForProximity should find the points of interests` () {
        val maxDistance = 10
        val builtPointOfInterest = PointOfInterestBuilder.makePointOfInterest().build()
        val pointOfInterestList = ArrayList<PointOfInterest>()
        pointOfInterestList.add(builtPointOfInterest)

        Mockito.`when`(mockPointOfInterestRepository.findForProximity(
                builtPointOfInterest.pointOfInterestCompositeId.x,
                builtPointOfInterest.pointOfInterestCompositeId.y,
                maxDistance
        )).thenReturn( pointOfInterestList )

        try {
            val returnedPointOfInterestList = pointOfInterestService.findForProximity(
                    builtPointOfInterest.pointOfInterestCompositeId.x.toString(),
                    builtPointOfInterest.pointOfInterestCompositeId.y.toString(),
                    maxDistance.toString()
            )

            Assert.assertEquals(pointOfInterestList, returnedPointOfInterestList)
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function findForProximity should not find point of interest when the entered have negative values` () {
        val maxDistance = -10
        val builtPointOfInterestWithNegativeCoordinates = PointOfInterestBuilder.makePointOfInterest().whithNegativeCoordinates().build()
        val pointOfInterestList = ArrayList<PointOfInterest>()
        pointOfInterestList.add(builtPointOfInterestWithNegativeCoordinates)

        Mockito.`when`(mockPointOfInterestRepository.findForProximity(
                builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId.x,
                builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId.y,
                maxDistance
        )).thenReturn( pointOfInterestList )

        try {
            pointOfInterestService.findForProximity(
                    builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId.x.toString(),
                    builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId.y.toString(),
                    maxDistance.toString()
            )

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NEGATIVE_VALUE.message)
        } catch (exception: BadRequestException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NEGATIVE_VALUE.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function findForProximity should not find point of interest when the entered have non integers values` () {
        val maxDistance = 10
        val nonIntegerValue = "string"
        val builtPointOfInterest = PointOfInterestBuilder.makePointOfInterest().build()
        val pointOfInterestList = ArrayList<PointOfInterest>()
        pointOfInterestList.add(builtPointOfInterest)

        Mockito.`when`(mockPointOfInterestRepository.findForProximity(
                builtPointOfInterest.pointOfInterestCompositeId.x,
                builtPointOfInterest.pointOfInterestCompositeId.y,
                maxDistance
        )).thenReturn( pointOfInterestList )

        try {
            pointOfInterestService.findForProximity(nonIntegerValue, nonIntegerValue, nonIntegerValue)

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NON_INTEGER_VALUE.message)
        } catch (exception: BadRequestException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NON_INTEGER_VALUE.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function findForProximity should not find points of interest near when they are not found elements consistent with those requested` () {
        val maxDistance = 10
        val builtPointOfInterestRequestedElement = PointOfInterestBuilder.makePointOfInterest().build()
        val emptyPointOfInterestList = ArrayList<PointOfInterest>()

        Mockito.`when`(mockPointOfInterestRepository.findForProximity(
                builtPointOfInterestRequestedElement.pointOfInterestCompositeId.x,
                builtPointOfInterestRequestedElement.pointOfInterestCompositeId.y,
                maxDistance
        )).thenReturn( emptyPointOfInterestList )

        try {
            pointOfInterestService.findForProximity(
                    builtPointOfInterestRequestedElement.pointOfInterestCompositeId.x.toString(),
                    builtPointOfInterestRequestedElement.pointOfInterestCompositeId.y.toString(),
                    maxDistance.toString()
            )

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.OBJECT_NOT_FOUND_FOR_REQUEST.message)
        } catch (exception: ObjectNotFoundException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.OBJECT_NOT_FOUND_FOR_REQUEST.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function insert should insert point of interest` () {
        val builtPointOfInterest = PointOfInterestBuilder.makePointOfInterest().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterest.pointOfInterestCompositeId))
                .thenReturn( Optional.empty() )

        Mockito.`when`(mockPointOfInterestRepository.save(builtPointOfInterest))
                .thenReturn( builtPointOfInterest )

        try {
            val returnedPointOfInterestList = pointOfInterestService.insert(builtPointOfInterest.pointOfInterestCompositeId)

            Assert.assertEquals(builtPointOfInterest, returnedPointOfInterestList)
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function insert should not insert point of interest when the entered coordinates have negative values`() {
        val builtPointOfInterestWithNegativeCoordinates = PointOfInterestBuilder.makePointOfInterest().whithNegativeCoordinates().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId))
                .thenReturn( Optional.empty() )

        Mockito.`when`(mockPointOfInterestRepository.save(builtPointOfInterestWithNegativeCoordinates))
                .thenReturn( builtPointOfInterestWithNegativeCoordinates )

        try {
            pointOfInterestService.insert(builtPointOfInterestWithNegativeCoordinates.pointOfInterestCompositeId)

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NEGATIVE_VALUE.message)
        } catch (exception: BadRequestException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_NEGATIVE_VALUE.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function insert should not insert point of interest when the entered name is blank` () {
        val builtPointOfInterestWithBlankName = PointOfInterestBuilder.makePointOfInterest().whithBlankName().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterestWithBlankName.pointOfInterestCompositeId))
                .thenReturn( Optional.empty() )

        Mockito.`when`(mockPointOfInterestRepository.save(builtPointOfInterestWithBlankName))
                .thenReturn( builtPointOfInterestWithBlankName )

        try {
            pointOfInterestService.insert(builtPointOfInterestWithBlankName.pointOfInterestCompositeId)

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_BLANK_NAME.message)
        } catch (exception: BadRequestException){
            Assert.assertTrue(exception.message!!.startsWith(PointOfInterestExceptionsMessagesEnum.BAD_REQUEST_BLANK_NAME.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

    @Test
    fun `function insert should not insert point of interest when found requested element` () {
        val builtPointOfInterest = PointOfInterestBuilder.makePointOfInterest().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterest.pointOfInterestCompositeId))
                .thenReturn( Optional.of(builtPointOfInterest) )

        Mockito.`when`(mockPointOfInterestRepository.save(builtPointOfInterest))
                .thenReturn( builtPointOfInterest )

        try {
            pointOfInterestService.insert(builtPointOfInterest.pointOfInterestCompositeId)

            Assert.fail("Expected exception did not occur! Expected exception = "+
                    ExceptionsMessagesEnum.UPGRADE_REQUIRED.message)
        } catch (exception: UpgradeRequiredException){
            Assert.assertTrue(exception.message!!.startsWith(ExceptionsMessagesEnum.UPGRADE_REQUIRED.message))
        } catch (exception: Exception){
            Assert.fail("Unexpected exception = "+exception.message)
        }
    }

}