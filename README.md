# The Scheduler

The Scheduler is a school timetable generator.<br>  
It creates schedule for both classes and teachers, making the most convenient arrangement of subjects in the educational grid for both categories.

The implementation of the genetic algorithm allows you:
- To create a schedule without coincidences, both for teachers and for groups of students
- Prevent or at least minimize the number of gaps in the schedule
- Distribute the load evenly over the days of the week

**Prerequisites:** <br> 
Intellij Idea IDE  <br> 
Java 18 and above <br> 
MySQL Workbench 

## Quick Start:
- [ ] Clone this repository.
- [ ] Open package with project in your IDE
- [ ] Import scheduler_create_script.sql file in your database (here you can see the eer.diagram)
- [ ] Populate the tables of this database with your data or use this data as an example.
- [ ] Set your database credendtials in db.properties file
- [ ] Run application

The algorithm works based on the initial set limits of a maximum of 5 working days and a maximum of 8 lessons per day.

After launching the application a scanner opens and requests, checks and accepts from the user the parameters for which it is necessary to create a schedule.
That information is about the desired number of days, the maximum and minimum lessons per day
(within the limits indicated above and based on the number of lessons presented in the database). -> (Sample input)

If the Application managed to generate a schedule, taking into account the entered parameters,
the user is given the opportunity to choose the type of information displayed in the terminal (ask about output example)
Depending on this choice, user can get the few options for displaying information on the screen: timetable for classes, timetable for teachers, or timetable for both categories. (1 option)
If, based on the specified conditions, the application failed to create a schedule, the user will receive a corresponding message (message) 
