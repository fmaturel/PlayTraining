TP05: Configuring logs
===

# About logs

A Log File is a file that records either events that occur in an operating system or other software runs, or messages between different users of a communication software.
Logging is the act of keeping a log. This is useful for all types of applications as it allows developpers to keep track of exceptions that are raised in the application.

Logging can manage messages from an application during its execution and allow immediate or differed operation. 
These messages are also very useful in the development of an application or during its operation to understand abnormal behaviour.

# Step 1: Add a configuration file clean logs to your project

The default logs available are interesting, but not enough to operate an application in a professional context.

We will define a new log file that records the application's performance:
- In the Resources tps, use the file Logger.xml and PerfLogger.java class to measure rendering the Back Office homepage.
- You will place the PerfLogger.java utils package in your project.

The result should be:
A log file / videostore.log containing, on almost course, the following logs
2015-03-05 00:07:29,079 - [INFO] - from play in play-internal-execution-context-1	Application started (Dev)
2015-03-05 00:07:29,241 - [TRACE] - from performances in play-akka.actor.default-dispatcher-4	Start[1425510449238] time[3] hr[3ms] tag[Rendu de la page de bienvenue du backoffice]

Next tutorial
====

Go to [TP06](https://github.com/fmaturel/PlayTraining/tree/master/play_tp06)
