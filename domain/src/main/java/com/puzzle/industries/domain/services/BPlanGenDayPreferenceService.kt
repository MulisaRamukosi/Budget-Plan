package com.puzzle.industries.domain.services

interface BPlanGenDayPreferenceService {
    fun saveDay(day: Int)
    fun getDay(): Int
}