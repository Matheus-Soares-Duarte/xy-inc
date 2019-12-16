package com.matheussd.xyinc.services

import com.matheussd.xyinc.builders.PointOfInterestBuilder
import com.matheussd.xyinc.repositories.PointOfInterestRepository
import com.matheussd.xyinc.services.exceptions.BadRequestException
import com.matheussd.xyinc.services.exceptions.ObjectNotFoundException
import com.matheussd.xyinc.services.exceptions.enums.PointOfInterestExceptionsMessagesEnum
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(BlockJUnit4ClassRunner::class)
class PointOfInterestServiceTest {
    @InjectMocks
    private var pointOfInterestService = PointOfInterestService()

    @Mock
    private lateinit var mockPointOfInterestRepository: PointOfInterestRepository

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun should_Find_PointOfInterest() {
        val builtPointOfInterest = PointOfInterestBuilder().makePointOfInterest().build()

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
    fun shouldNot_Find_PointOfInterest_WhenNotFoundRequestedElement() {
        val builtPointOfInterestRequestedElement = PointOfInterestBuilder().makePointOfInterest().build()

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
    fun shouldNot_Find_PointOfInterest_WithBlankName() {
        val builtPointOfInterestWithBlankName = PointOfInterestBuilder().makePointOfInterest().whithBlankName().build()

        Mockito.`when`(mockPointOfInterestRepository.findById(builtPointOfInterestWithBlankName.pointOfInterestCompositeId))
                .thenReturn(Optional.of(builtPointOfInterestWithBlankName))

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
    fun shouldNot_Find_PointOfInterest_WithNegativeCoordinates() {
        val builtPointOfInterestWithNegativeCoordinates = PointOfInterestBuilder().makePointOfInterest().whithNegativeCoordinates().build()

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
    fun shouldNot_Find_PointOfInterest_WithNonIntegerCoordinates() {
        val nonIntegerCoordinate = "string"
        val builtPointOfInterest  = PointOfInterestBuilder().makePointOfInterest().build()

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

}