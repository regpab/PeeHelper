package com.regpab.peehelper.data

import com.regpab.peehelper.domain.PeePredictService
import com.regpab.peehelper.domain.UrinalState
import com.regpab.peehelper.domain.Urinals

class PeePredictServiceV5 : PeePredictService {
    override fun predict(urinals: Urinals): Int {
        val urinalsStates = urinals.states
        val size = urinalsStates.size
        var weights = HashMap<Int, Int>()
        urinalsStates.forEachIndexed { index, it ->
            if (it != UrinalState.BUSY) {
                weights[index] = 0
            }
        }
        if (weights.size == 0)
            return 0
        var step = 1
        while (step < size) {
            weights.keys.forEach {
                //check left values
                val leftNeighborIndex = it - step
                if (leftNeighborIndex >= 0) {
                    if (urinalsStates[leftNeighborIndex] == UrinalState.BUSY) {
                        weights[it] = (weights[it] ?: 0) + 1
                    }
                }
                //check right values
                val rightNeighborIndex = it + step
                if (rightNeighborIndex <= urinalsStates.lastIndex) {
                    if (urinalsStates[rightNeighborIndex] == UrinalState.BUSY) {
                        weights[it] = (weights[it] ?: 0) + 1
                    }
                }
            }
            // remove irrelevant positions
            val minimumNeighbors = weights.values.min()
            weights = HashMap(weights.filterValues { it == minimumNeighbors })

            step++
        }
        return weights.keys.random()
    }
}