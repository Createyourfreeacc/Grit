/*
 * Copyright (C) 2026  Shubham Gorai
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.shub39.grit.core.habits.domain

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlinx.serialization.Serializable

@Serializable
data class Habit(
    val id: Long = 0,
    val title: String,
    val description: String,
    val time: LocalDateTime,
    val days: Set<DayOfWeek>,
    val index: Int,
    val reminder: Boolean,
    val scheduleType: ScheduleType = ScheduleType.WEEKLY,
    val daysOfMonth: Set<Int> = emptySet(),
)

fun Habit.matches(date: LocalDate): Boolean = when (scheduleType) {
    ScheduleType.WEEKLY -> date.dayOfWeek in days
    ScheduleType.MONTHLY -> date.dayOfMonth in daysOfMonth || overflowDayOnOrNull(date) != null
}

// If [date] is a rollover for a non-existent scheduled day in the previous month
// (e.g. asked for the 31st in a 30-day month), return the original day-of-month.
// Otherwise null. Monthly habits only.
fun Habit.overflowDayOnOrNull(date: LocalDate): Int? {
    if (scheduleType != ScheduleType.MONTHLY) return null
    if (date.dayOfMonth > 3) return null
    if (daysOfMonth.all { it <= 28 }) return null
    val lastOfPrevMonth = LocalDate(date.year, date.month, 1).minus(1, DateTimeUnit.DAY)
    val daysInPrev = lastOfPrevMonth.dayOfMonth
    val wouldBe = daysInPrev + date.dayOfMonth
    return if (wouldBe > daysInPrev && wouldBe in daysOfMonth) wouldBe else null
}
