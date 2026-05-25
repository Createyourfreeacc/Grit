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

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.shub39.grit.widgets.all_tasks_widget.AllTasksWidgetReceiver
import com.shub39.grit.widgets.habit_overview_widget.HabitOverviewWidgetReceiver
import com.shub39.grit.widgets.habit_streak_widget.HabitStreakWidgetReceiver
import com.shub39.grit.widgets.habit_week_chart_widget.HabitWeekChartWidgetReceiver
import org.koin.core.annotation.Single

@Single
class WidgetUpdater(private val context: Context) {

    // Send the standard Android APPWIDGET_UPDATE broadcast targeted at one receiver.
    // This is more reliable than GlanceAppWidget.updateAll(): it routes through each
    // receiver's ComponentName explicitly, so there is no shared lookup map that can
    // cross-contaminate widget classes under concurrent calls.
    private fun broadcastUpdate(receiverClass: Class<*>) {
        val component = ComponentName(context, receiverClass)
        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(component)
        if (ids.isEmpty()) return
        val intent =
            Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
                this.component = component
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            }
        context.sendBroadcast(intent)
    }

    fun refreshTaskWidgets() {
        runCatching { broadcastUpdate(AllTasksWidgetReceiver::class.java) }
    }

    fun refreshHabitWidgets() {
        runCatching { broadcastUpdate(HabitOverviewWidgetReceiver::class.java) }
        runCatching { broadcastUpdate(HabitStreakWidgetReceiver::class.java) }
        runCatching { broadcastUpdate(HabitWeekChartWidgetReceiver::class.java) }
    }
}
