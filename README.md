# Time Sheet Aggregation

Time sheet files have the following pattern `<year.month>-time-sheet.csv`. They contain multiple time sheet entries.
Each row represents one activity on a certain day, for an amount of hours.

Activities are in format `main-activity.sub-activity`. These activities can be used to filter the entries.

```
year; week; day; date; hours; activity
2020; 01; Fri; 2020.01.03; 8; sample.programming
2020; 01; Thu; 2020.01.02; 4; sample.programming
2020; 01; Thu; 2020.01.02; 4; sample.testing
                         ; 2; sample.travel
2020; 01; Wed; 2020.01.01; ; (New Year's Day)
```


## Architecture

TimeSheet
- activities
- getTImeSheetPerWeek
- getTImeSheetPerMonth

