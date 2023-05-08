package com.regpab.peehelper.domain

data class Urinals(val states: List<UrinalState>)

enum class UrinalState {
    FREE,
    BUSY
}
