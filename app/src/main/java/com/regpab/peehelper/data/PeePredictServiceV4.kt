package com.regpab.peehelper.data

import com.regpab.peehelper.domain.PeePredictService
import com.regpab.peehelper.domain.UrinalState
import com.regpab.peehelper.domain.Urinals
import kotlin.math.abs

// problem xoxxoxox
class PeePredictServiceV4 : PeePredictService {
    override fun predict(urinals: Urinals): Int {
        val urinalsStates = urinals.states
        val size = urinalsStates.size
        val maxDistance = size - 1
        val weights = Array(size) {
            Distance(
                it,
                maxDistance - it,
                it == 0 || it == maxDistance
            )
        }
        urinalsStates.forEachIndexed { indexOfState, state ->
            if (state == UrinalState.BUSY) {
                for (indexOfWeight in weights.indices) {
                    val distance = indexOfState - indexOfWeight
                    if (distance < 0) {
                        val weight = weights[indexOfWeight]
                        if (weight.left > abs(distance))
                            weight.left = abs(distance)
                    } else if (distance > 0) {
                        val weight = weights[indexOfWeight]
                        if (weight.right > abs(distance))
                            weight.right = abs(distance)
                    } else {
                        val weight = weights[indexOfWeight]
                        weight.left = 0
                        weight.right = 0
                    }
                }
            }
        }

        println("Weights: ${weights.toList()}")

        var maxDistanceValue = weights[0]

        weights.forEach loop@{ distance ->
            if (distance.left == 0 && distance.right == 0)
                return@loop
            if (maxDistanceValue.isEdge) {
                if ((maxDistanceValue.left == 0 && minOf(
                        distance.left, distance.right
                    ) > maxDistanceValue.right)
                    ||
                    (maxDistanceValue.right == 0 && minOf(
                        distance.left, distance.right
                    ) > distance.left)
                ) {
                    maxDistanceValue = distance
                }
            } else {
                if (maxDistanceValue.value < distance.value) {
                    maxDistanceValue = distance
                } else if (maxDistanceValue.value == distance.value && maxDistanceValue.delta > distance.delta) {
                    maxDistanceValue = distance
                } else if (distance.isEdge &&
                    ((distance.left == 0 && minOf(
                        maxDistanceValue.left,
                        maxDistanceValue.right
                    ) <= distance.right)
                            ||
                            (distance.right == 0 && minOf(
                                maxDistanceValue.left,
                                maxDistanceValue.right
                            ) <= distance.left))
                ) {
                    maxDistanceValue = distance
                }
            }
        }

        println("Max weight: $maxDistanceValue")

        val maxWeightsIndexes = arrayListOf<Int>()
        weights.forEachIndexed { index, i ->
            if (i.value == maxDistanceValue.value && i.delta == maxDistanceValue.delta) {
                maxWeightsIndexes.add(index)
            }
        }

        println("Max weights indices: $maxWeightsIndexes")

        if (maxWeightsIndexes.size == 1) {
            return maxWeightsIndexes[0]
        }

        val middleIndex = (size - 1) / 2f

        println("Center of array: $middleIndex")

        val maxRangeToCenter = maxWeightsIndexes.maxOf { abs(middleIndex - it) }

        println("Max range to center: $maxRangeToCenter")

        val distantFromCenterWeights =
            maxWeightsIndexes.filter { abs(middleIndex - it) == maxRangeToCenter }

        println("Distant from center weights: $distantFromCenterWeights")

        return if (distantFromCenterWeights.size == 1) {
            distantFromCenterWeights[0]
        } else {
            println(distantFromCenterWeights)
            distantFromCenterWeights.random()
        }
    }

    data class Distance(var left: Int, var right: Int, val isEdge: Boolean = false) {
        val value: Int
            get() = left + right

        val delta: Int
            get() = if (!isEdge) abs(left - right) else -1

        override fun toString(): String {
            return "($left, $right)"
        }
    }
}