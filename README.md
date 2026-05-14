# Grit (fork)
 
A fork of [shub39/Grit](https://github.com/shub39/Grit) — a simple todo list and habit tracker for Android — with behavior and feature changes aimed at making it more usable as a daily supplement / routine tracker.
 
## Changes in this fork
 
### Completed items stay in place
Tasks and habits marked as done are crossed out but remain in their original position, in both the in-app list and the homescreen widget. Upstream provides a "Reorder tasks" setting that pushes completed items to the bottom; this fork removes that reordering behavior (and the setting that controlled it) so the list order is stable as you tick things off.
 
### Configurable default day-time window for habits
A new setting defines what time range counts as "a day" for habit scheduling — for example, 7:00 to 23:00. When you create a new habit, this becomes the default active window instead of a single point-in-time reminder.
 
### Missed-habit notifications
Habits now support a separate "missed" notification in addition to the existing "due" reminder. If a habit is scheduled for a given day and is still uncompleted by the end of its active window, a notification fires. Toggled independently of the due notification on a per-habit basis.
 
### Longer, scrolling descriptions
The 50-character cap on habit descriptions is lifted. In the widget, descriptions that exceed the available width scroll horizontally (marquee) instead of being clipped, so the full text stays readable at a glance.
 
### Monthly habit schedules
Habits can be scheduled by day-of-month in addition to day-of-week. A habit can now be set to "the 1st and 2nd of every month" — useful for periodic supplements, monthly chores, etc. The existing weekly schedule continues to work; each habit picks one or the other.
 
## Original project
 
All credit for the underlying app goes to [shub39](https://github.com/shub39) and the Grit contributors. See upstream for the original README, screenshots, and roadmap:
 
- Upstream repo: https://github.com/shub39/Grit
- Roadmap discussion: https://github.com/shub39/Grit/discussions/66
- Translations (Weblate): https://hosted.weblate.org/engage/grit/
