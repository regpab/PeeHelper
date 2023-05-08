package com.regpab.peehelper

import com.regpab.peehelper.data.*
import com.regpab.peehelper.domain.PeePredictService
import com.regpab.peehelper.domain.UrinalState
import com.regpab.peehelper.domain.Urinals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class PeeTest {

    private lateinit var peePredictService: PeePredictService

    @Before
    fun init() {
        peePredictService = PeePredictServiceV5()
    }

    @Test
    fun peeProblemEasy() {
        val urinals = Urinals(
            listOf(
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY
            )
        )

        peePredictService.assertResultSafe(urinals, 2)
    }

    @Test
    fun peeProblemEasy2() {
        val urinals = Urinals(
            listOf(
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY
            )
        )

        peePredictService.assertResultSafe(urinals, 0)
    }

    @Test
    fun peeProblemEasy3() {
        val urinals = Urinals(
            listOf(
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
            )
        )

        peePredictService.assertResultSafe(urinals, 5)
    }

    @Test
    fun peeProblemMedium() {
        val urinals = Urinals(
            listOf(
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.BUSY
            )
        )

        peePredictService.assertResultSafe(urinals, 2)
    }

    @Test
    fun peeProblemMedium2() {
        val urinals = Urinals(
            listOf(
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.BUSY,
            )
        )

        peePredictService.assertResultSafe(urinals, 5)
    }

    @Test
    fun peeProblemMedium3() {
        val urinals = Urinals(
            listOf(
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.BUSY,
            )
        )

        peePredictService.assertResultSafe(urinals, 0)
    }

    @Test
    fun peeProblemHard() {
        val urinals = Urinals(
            listOf(
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.BUSY,
            )
        )

        peePredictService.assertResultSafe(urinals, 1)
    }

    @Test
    fun peeProblemHard2() {
        val urinals = Urinals(
            listOf(
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.BUSY,
            )
        )

        peePredictService.assertResultSafe(urinals, 6)
    }

    @Test
    fun peeProblemRandom() {
        val urinals = Urinals(
            listOf(
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.FREE,
                UrinalState.BUSY,
            )
        )

        repeat(100) {
            val result = peePredictService.predict(urinals)

            assert(listOf(2, 6).contains(result))
        }
    }

    private fun PeePredictService.assertResultSafe(urinals: Urinals, expected: Int) {
        repeat(100) {
            val result = predict(urinals)

            assertEquals(expected, result)
        }
    }
}