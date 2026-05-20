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
package com.shub39.grit.core.data

import androidx.room3.TypeConverter
import com.shub39.grit.core.habits.domain.ScheduleType
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object Converters {
    val allDays = dayOfWeekToString(DayOfWeek.entries.toSet())

    @TypeConverter
    fun dayOfWeekToString(value: Set<DayOfWeek>): String {
        return value.joinToString(",") { it.name }
    }

    @TypeConverter
    fun dayOfWeekFromString(value: String): Set<DayOfWeek> {
        return if (value.isBlank()) emptySet()
        else value.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    }

    @TypeConverter
    fun daysOfMonthToString(value: Set<Int>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun daysOfMonthFromString(value: String): Set<Int> {
        return if (value.isBlank()) emptySet()
        else value.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
    }

    @TypeConverter
    fun scheduleTypeToString(value: ScheduleType): String = value.name

    @TypeConverter
    fun scheduleTypeFromString(value: String): ScheduleType =
        ScheduleType.entries.find { it.name == value } ?: ScheduleType.WEEKLY

    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun dateFromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            Instant.fromEpochSeconds(value).toLocalDateTime(TimeZone.currentSystemDefault())
        }
    }

    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(TimeZone.currentSystemDefault())?.epochSeconds
    }

    @TypeConverter
    fun dayFromTimestamp(value: Long): LocalDate {
        return value.let { LocalDate.fromEpochDays(value) }
    }

    @TypeConverter
    fun dayToTimestamp(date: LocalDate): Long {
        return date.toEpochDays()
    }
}
