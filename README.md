<a name="The_Scheduler"></a> 
# The Scheduler 

The Scheduler is a school timetable generator.<br>  
It creates schedule for both classes and teachers, making the most convenient arrangement of subjects in the educational grid.

The implementation of the genetic algorithm allows you:
- To create a schedule without overlaps
- Prevent or at least minimize the number of gaps in the schedule
- Distribute the load evenly over the days of the week

## Quick Start:

**Prerequisites:** <br> 
Intellij Idea IDE  <br> 
Java 18 and above <br> 
MySQL Workbench 

- [ ] Clone this repository.
- [ ] Open package with project in your IDE
- [ ] Import [scheduler_create_script.sql](https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/db/scheduler_create_script.sql) file in your database (here you can see the [eer.diagram](https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/db/scheduler_eer_diagram.png))
- [ ] Populate the tables of this database with your data or use [insert_queries_example.sql](https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/db/insert_queries_example.sql) as an example.
- [ ] Set your database credendtials in [db.properties file](https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/db.properties)
- [ ] Run application

## Application process
The algorithm works based on the initial set limits of a maximum of `5 working days` and a maximum of `8 lessons per day`.

After launching, the Application asks you, validates and accepts the parameters for which it is necessary to create a schedule: <br>
what `number of working days` per week should be taken into account, the `maximum lessons per day` and `minimum lessons per day` taking into account the limits indicated above, as well as based on the number of lessons presented in the database.

<img src="https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/forReadme/scanner_input.jpg" width="350">

If the Application managed to generate a schedule, taking into account the entered parameters,
you will get the opportunity to choose the type of information displayed in the terminal:

<img src="https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/forReadme/ask_about_output.jpg" width="500">

Depending on this choice, you can get the few options for displaying information on the screen: 
`schedule for groups`, `schedule for teachers`, or `schedule for both categories`:

<img src="https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/forReadme/sample_output.PNG" width="700">

If, based on the specified conditions, the Application failed to create a schedule, you will receive a corresponding message:

<img src="https://github.com/yana-glt/scheduler/blob/prerelease/src/main/resources/forReadme/message.PNG" width="500">

:arrow_up: [up](#The_Scheduler)
