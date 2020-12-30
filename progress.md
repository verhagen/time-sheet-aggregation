# Progress


## App
- [ ] Create main application
- [ ] Add command line interface (cli)

## Aggregator
- [ ] Add test case with empty entries (not full month filled)  
      (Banking) Holiday
- [ ] 
- [ ] 
- [ ] 
- [ ] 

## AggregatorPerWeek
- [/] Create output per week per client bookable hours and none bookable hours (self[*] + travel time)
- [ ] Total per week
- [ ] Total per month

## Convertor - Load from SAP file, add to file <year.month>-time-sheet.csv
- [ ] Read content from SAP-text
- [ ] Read content from SAP-tabs
- [ ] Create TimeSheetEntries
- [ ] Add new TimeSheetEntries to existing time-sheet file  
      Remove empty entries, where possible

## General improvements
- [/] Make Activity names a tree (format: `activity.activity` similar to log instance tree). Instead two fixed layers of main and sub activity (format: `main-activity[sub-activity]`)  
    - [x] Create png of activity graph
- [ ] Move group-by / filter to the ShowWeekPerYear, instead of 
- [ ] Register time of activity in hours and minutes (not as float)
- [x] Add optional a description to each activity (in Markdown)

## Test Cases
- [ ] test total hours per week / month for (group-by) activities
- [ ] test over multiple months
- [ ] test few entries per week, multiple months
- [ ] test with some empty weeks, in multiple month (vacation / not working on some projects)
- [ ] test how many hours per week / month / year, for certain (group-by) activit(y/ies)
- [ ] 
