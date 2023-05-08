package com.regpab.peehelper.domain

interface PeePredictService {
    fun predict(urinals: Urinals): Int
}