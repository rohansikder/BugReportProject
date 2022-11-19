# Bug Report

Multi-threaded TCP Server Application which allows multiple users to be registers and bug reports to be added or updates.

1. Register with the system (Note Employee ID and Email must be unique)
  * Name
  * Employee ID
  * Email
  * Department

2. Log-in to the bug tracker system from the client application to the server application.

3. Add a bug record with the following details (Note when adding a bug report the server should assign a unique bug ID).
  * Application Name
  * Date and Time Stamp
  * Platform (e.g. Window, Unix or Mac)
  * Problem Description
  * Status (Can be Open, Assigned or Closed)

4. Assign the bug record to a registered user using a bug ID.

5. View the all the bug record not assigned to any developer.

6. Update the status of bug records details to be either of the following:
  * Open
  * Closed
  * Assigned
  
### Server Application Rules
1. The server application should not provide of options 3-6 to a client application that has not completed the authentication.
2. The server should hold a list of valid users of the service and a list of all the bugs previously registered with the systems.
3. All of client applications should be able to see all the users and bug added by all the client applications.
