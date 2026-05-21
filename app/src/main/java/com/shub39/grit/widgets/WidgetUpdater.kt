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
package com.shub39.grit.widgets

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.shub39.grit.widgets.all_tasks_widget.AllTasksWidget
import com.shub39.grit.widgets.habit_overview_widget.HabitOverviewWidget
import com.shub39.grit.widgets.habit_streak_widget.HabitStreakWidget
import com.shub39.grit.widgets.habit_week_chart_widget.HabitWeekChartWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
class WidgetUpdater(private val context: Context) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun refreshTaskWidgets() {
        scope.launch { runCatching { AllTasksWidget().updateAll(context) } }
    }

    fun refreshHabitWidgets() {
        // Each in its own coroutine so a failure in one does not suppress the others.
        scope.launch { runCatching { HabitOverviewWidget().updateAll(context) } }
        scope.launch { runCatching { HabitStreakWidget().updateAll(context) } }
        scope.launch { runCatching { HabitWeekChartWidget().updateAll(context) } }
    }
}
