package com.regpab.peehelper.data

import com.regpab.peehelper.domain.PeePredictService
import com.regpab.peehelper.domain.UrinalState
import com.regpab.peehelper.domain.Urinals
import kotlin.math.abs

class PeePredictServiceV1 : PeePredictService {
    override fun predict(urinals: Urinals): Int {
        val urinalsStates = urinals.states
        val size = urinalsStates.size
        val weights = IntArray(size)
        val maxWeight = size - 1
        urinalsStates.forEachIndexed { indexOfState, state ->
            if (state == UrinalState.BUSY) {
                for (indexOfWeight in weights.indices) {
                    val weightOfStateInCurrentIndex = maxWeight - abs(indexOfState - indexOfWeight)
                    if (weights[indexOfWeight] < weightOfStateInCurrentIndex) {
                        weights[indexOfWeight] = weightOfStateInCurrentIndex
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

        if (minWeightsIndexes.size == 1) {
            return minWeightsIndexes[0]
        }

        val middleIndex = (size - 1) / 2f

        println("Center of array: $middleIndex")

        val maxRangeToCenter = minWeightsIndexes.maxOf { abs(middleIndex - it) }

        println("Max range to center: $maxRangeToCenter")

        val distantFromCenterWeights =
            minWeightsIndexes.filter { abs(middleIndex - it) == maxRangeToCenter }

        println("Distant from center weights: $distantFromCenterWeights")

        return if (distantFromCenterWeights.size == 1) {
            distantFromCenterWeights[0]
        } else {
            println(distantFromCenterWeights)
            distantFromCenterWeights.random()
        }
    }
}