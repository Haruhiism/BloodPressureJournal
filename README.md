# Blood Pressure Journal
Android app that serves as a journal/log of blood pressure readings. 
~Under construction!~



## Daily Readings
- [x] Scatter Chart that displays the day's readings
- [x] Addition of a reading via FAB
- [x] Dialog that prompts the user to select the reading to store (single or average of 3)
- [x] Display last 5 readings under scatter chart
- [ ] Allow viewing/editing a reading in detail when tapping on a given item from the RV

![Daily Readings](https://i.ibb.co/TR6RNLm/Screenshot-1631388696.png)
![FAB Dialog](https://i.ibb.co/k09ppzN/Screenshot-1631388701.png)

## Add New Reading
- [x] Implement Tabbed Layout within the fragment 
- [x] Use NumberPicker library for a quick and easy way to log reading information values (systolic, diastolic, pulse)
- [x] Preserve the content the user has set on each NumberPicker widget accross changs within the selected fragment/configuration changes (used ViewModel)

![Add New Reading](https://i.ibb.co/80bTWgh/Screenshot-1631388719.png)

## Statistics
- [x] Implement Spinner widget at the very top that allows the user to select a date range to display their data
- [x] Use MaterialDatePicker to set a dialog that allows the user to pick a custom date range
- [x] Display the user's data on a RecyclerView
- [x] Implement a Pie Chart widget to visualize the percentage ranges that the user's readings fell within  
- [ ] Allow viewing/editing a reading in detail when tapping on a given item from the RV
- [ ] Implement the funcitonality of the export button on the Nav Bar (to export user's data to pdf/excel)

![MaterialDatePicker](https://i.ibb.co/hDqWs4k/Screenshot-1633122219.png)
![Statistics](https://i.ibb.co/gdKnyxW/Screenshot-1633122231.png)

## Settings
- [x] Implement dark mode funcitonality
- [ ] Iron out a few issues with how data is displayed within dark mode (some text not converting to contrasting color automatically)
- [ ] More settings as needed
![Dark mode](https://i.ibb.co/JtRybj2/Screenshot-1633124145.png)

## Work in Progress:
### Implementing medication reminders 
- [x] Implement medication & reminder entities to model the data
- [x] Set up appropriate navigation components
- [ ] Setup AlarmManager to set the reminders
- [ ] Set up appropriate Broadcast receivers (ONBOOT will be required in Manifest file to reset the reminders upon device restart)
- [ ] Reminders will be daily and can be repeated on a user defined interval throughout the day (i.e. twice a day: 9am & 9pm, etc)
  * Note that since medications are usually taken daily, not considering allowing the user to select the days to repeat. However, it may be an option later.
- [ ] Make appropriate notification channels for the reminders
![MedicationReminders Fragment](https://i.ibb.co/wwLtZx5/Screenshot-1633311515.png)
![Add/Edit Medication](https://i.ibb.co/kgj5jpb/Screenshot-1633311535.png)

## Terrms & Conditions
- [x] Implement the T&C fragment
- [ ] Add the appropriate disclaimers & such

## Planned Features (not currently in progress)
1. Add a tag/comment system that allows the user to attatch tags to their reading entries for them to sort by later
  * Tags could also be added to the exported data file and could be used as the main filter if the user desires
