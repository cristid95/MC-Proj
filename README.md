# Reminder application

# Description
The application allows users to create, manage and delete reminders. They will be reminded of the events through Push Notifications.

### Spoilers
The folder app_screenshots contains screens representing activities of the application. They offer a brief overview of how the application looks and works.

### Reminders synchronization across multiple devices
In order for the reminders to be synchronized on all user devices, Firebase is used to store them. When the user attempts to install the application on a new device, the reminders will be automatically pulled from Firebase and their notifications will be set up.

### Start using the app
When a new user wants to start using the application, he/she must be first added to the list of allowed users in order to have access to Firebase. This can be done from the Firebase console. For the moment, only a user has access to the database. After being added to the list, the user can login into the application with the registered email and password. This is mandatory in order to ensure that each user has access only to his/her own data, and no other user can read/write data that does not belong to him/her. 

### Reminder create
The user can create a new reminder by pressing the green button from the bottom right part of the Main Activity. A new activity will appear and the user is able to provide values for the following fields of the reminder:
- title (mandatory; a title of length 0 will lead to the reminder being ignored and discarded)
- description
- start date (DD-MM-YYYY)
- start time (HH:MM)
- repetition interval; by leaving this box empty, the reminder will pop up only once, at the specified date and time, otherwise periodic notifications will pe pushed for that reminder. The repetition interval can be expressed by using years, months, days, hours, minutes and/or seconds as measurement units.
Once the details are typed in and the "Save" button is pressed, the reminder will be saved and it will be displayed in the list from the Main Activity.

### Reminder edit
A reminder can be edited by clicking on its name from the list containing the active reminders displayed in the Main Activity. A new activity will appear and the user is able to change any attribute (title, description, start date and time and repetition interval). Note that if the title is erased and the "Save" button is pressed, the change will have no effect (i.e. the title will keep its old value).

### Reminder delete
A reminder can be edited by clicking on its name from the list containing the active reminders displayed in the Main Activity. A new activity will appear (the same one as for editing the reminder) and the user should press the "Delete" button. A confirmation message will be displayed, and if the user confirms the deletion, the reminder will be discarded and the changes will be reflect immediately on the Main Activity content.
