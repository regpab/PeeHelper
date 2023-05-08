package com.regpab.peehelper.data

import com.regpab.peehelper.domain.PeePredictService
import com.regpab.peehelper.domain.UrinalState
import com.regpab.peehelper.domain.Urinals
import kotlin.math.abs

class PeePredictServiceV2 : PeePredictService {
    override fun predict(urinals: Urinals): Int {
        val urinalsStates = urinals.states
        val size = urinalsStates.size
        val weights = IntArray(size)
        val maxWeight = size - 1
        urinalsStates.forEachIndexed { indexOfState, state ->
            if (state == UrinalState.BUSY) {
                for (indexOfWeight in weights.indices) {
                    val weightOfStateInCurrentIndex = maxWeight - abs(indexOfState - indexOfWeight)
                    if (indexOfWeight == indexOfState) {
                        weights[indexOfWeight] = Int.MAX_VALUE
                    } else if (weights[indexOfWeight] < Int.MAX_VALUE) {
                        weights[indexOfWeight] += weightOfStateInCurrentIndex
                    }
                }
            }
        }

        println("Weights: ${weights.toList()}")

        val minWeight = weights.min()

        println("Minimal weight: $minWeight")

        val minWeightsIndexes = arrayListOf<Int>()
        weights.forEachIndexed { index, i ->
            if (i == minWeight) {
                minWeightsIndexes.add(index)
            }
        }

        println("Minimal weights indices: $minWeightsIndexes")

        return minWeightsIndexes.random()
    }
}