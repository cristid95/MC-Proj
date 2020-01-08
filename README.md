# MC-Proj

# Description

The application allows users to create, manage and delete reminders. They will be reminded of the events through Push Notifications.

Reminder creation
The user can create a new reminder by pressing the green button from the bottom right part of the Main Activity. A new activity will appear and the user is able to provide values for the following fields of the reminder:
- title (mandatory; a title of length 0 will lead to the reminder being ignored and discarded)
- description
- start date (DD-MM-YYYY)
- start time (HH:MM)
- repetition interval; by leaving this box empty, the reminder will pop up only once, at the specified date and time, otherwise periodic notifications will pe pushed for that reminder. The repetition interval can be expressed by using years, months, days, hours, minutes and/or seconds as measurement units.
Once the details are typed in and the "Save" button is pressed, the reminder will be saved and it will be displayed in the list from the Main Activity.

Reminder edit
A reminder can be edited by clicking on its name from the list containing the active reminders displayed in the Main Activity. A new activity will appear and the user is able to change any attribute (title, description, start date and time and repetition interval). Note that if the title is erased and the "Save" button is pressed, the change will have no effect (i.e. the title will keep its old value).

Reminder delete
A reminder can be edited by clicking on its name from the list containing the active reminders displayed in the Main Activity. A new activity will appear (the same one as for editing the reminder) and the user should press the "Delete" button. A confirmation message will be displayed, and if the user confirms the deletion, the reminder will be discarded and the changes will be reflect immediately on the Main Activity content.
