TP01: Install Play! and the appropriate tools
====

# Step 1: Getting and Installing programs used in the course

## Retrieving binaries

Follow the trainer's to retrieve the binaries of following programs:

| Program | Version | Use |
| :------------ |:---------------:| -----:|
| Java JDK 6, 7 or 8 | 7u51 | Java Development Kit containing common classes required for any development in Java |
| Play! Framework 2 | 2.3.8 (02/2015) | This is the Java Web Framework studied during the course! Source: http://www.playframework.com |
| Eclipse with Scala | 4.0.0 | The Eclipse IDE enhanced with a Scala editor and plugin for Play! Framework. Source: http://scala-ide.org |

## Installation @Home

To work with the Play! Framework, You need JDK 6 or later.
- If you are a Mac OS user, Java is integrated.
- If you are a Linux user, make sure you use the Sun JDK or OpenJDK (no GCJ, which is the distribution of Java by default on many distributions).
- If you use Windows, simply download and install the latest JDK.

1. Create the "play" directory and unzip Play! Framework in this directory.
Check that the directory "./play/play-2.2.1 " is created.

2. Add the play script in your system "path":

For UNIX, make sure the script is executable.
Otherwise, do a:
```
chmod a+x play
chmod a+x framework/build
```

Then add in the ~/.bashrc:
```
export PATH=$PATH:/relativePath/to/play
```

On Windows, you'll need to set the path in the global environment variables.
That means updating the PATH variable in the environment and do not use a path with spaces.

Note: Java 7 (before update on MacOS 9) has a bug that causes problems with iteratees.
If you are using Java 7 on Mac OS, make sure you use the latest version.

# Step 2: Getting Started with "the play command"

## Available commands

The "play help" command informs about available actions:
```
C:\play\tps>play help
       _
 _ __ | | __ _ _  _
| '_ \| |/ _' | || |
|  __/|_|\____|\__ /
|_|            |__/

play 2.2.1 built with Scala 2.10.2 (running Java 1.7.0_51), http://www.playframework.com
Welcome to Play 2.2.1!

These commands are available:
-----------------------------
license            Display licensing informations.
new [directory]    Create a new Play application in the specified directory.

You can also browse the complete documentation at http://www.playframework.com.
```

## Creating our application

This shows that only two commands are available for now!
We will create a new play project through the "new  command.

This task will require the following information:
The name of the application (only for display, this name will be used later in several messages).
The type of skeleton to use for this application.

You should choose the Java application type that will be studied during the course.
```
C:\play\tps>play new videostore
…
The new application will be created in C:\play\tps\videostore

What is the application name? [videostore]
>
Which template do you want to use for this new application?

  1             - Create a simple Scala application
  2             - Create a simple Java application
> 2
OK, application videostore is created.
Have fun!
```

Our skeleton Java application is created.
A "tree" command will show us how this skeleton is built:

```
C:\play\tps>tree
Folder PATH listing for volume xxx
Volume serial number is xxx
C:
.
└ ─ ─ ─ videostore
    ├ ─ ─ ─ app
    │ ├ ─ ─ ─ controllers
    │ └ ─ ─ ─ views
    ├ ─ ─ ─ conf
    ├ ─ ─ ─ Project
    ├ ─ ─ ─ public
    │ ├ ─ ─ ─ pictures
    │ ├ ─ ─ ─ javascripts
    │ └ ─ ─ ─ stylesheets
    └ ─ ─ ─ test
```

We will cover each directories' purpose later on.

## Starting the application

Execute your application to watch network traffic between our web application and a web browser.
To achieve this, use the "run" command to start the play server in "development" mode.
Simply invoke the application directory (do not forget the "cd videostore")

NB: at first start, play retrieves needed libraries in a repository project like MAVEN does.
We will see how to use this system for handling dependencies later on.

```
C:\play\tps\videostore>play run
[info] play - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Ctrl+D to stop and go back to the console...)

[info] Compiling 4 Scala sources and 2 Java sources to D:\play\tps\videostore\target\scala-2.10\classes...
[info] play - Application started (Dev)
```

Interesting information :
- Note that the application is started by default on port 9000 .
- Play says that Ctrl + D can stop the application.
- We see that play compiles sources on the fly.

Finally, start a browser and go to http://localhost:9000

We get the home screen of the "example" play! application.

Warning: Rather than using the "run" command you can use "~ run"
Play! then compiles on the fly when detects changes in the code (not only when a query is issued).
This will greatly accelerate your work.

# Step 3: Analysis of network flows, reminders on HTTP protocol

## Network Analysis
Use the network analyzer of your favorite browser
Go to: http://localhost:9000

Check request and response's headers and  body:
```
Request URL:http://localhost:9000/
Request Method:GET
Status Code:200 OK

Request Headers 
GET / HTTP/1.1
Host: localhost:9000
Connection: keep-alive
Cache-Control: max-age=0
Accept: text/html,application/xhtml+xml, application/xml;q=0.9, image/webp,*/*;q=0.8
User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.117 Safari/537.36
Accept-Encoding: gzip,deflate,sdch
Accept-Language: en-US,en;q=0.8,fr;q=0.6

Response Headers
HTTP/1.1 200 OK
Content-Type: text/html; charset=utf-8
Content-Length: 6808
```

Time to have a break (coffee!)  

# Step 4: Install and launch eclipse scala for code edition

## Configuring Eclipse IDE for Play!
In the directory "C:\play" previously created, extract the zip Eclipse IDE for Scala.
You must have the directory "C:\play\eclipse" created.

### Create an application shortcut to the desktop eclipse.exe

```
C:\play\eclipse\eclipse.exe
```

### And run the application.
We will now prepare our project "videostore" in order to import it into eclipse scala.
The play console starts with the "play" command in the root directory of your application.
Then the "help play" command is used for the various valid orders on our project.

```
D:\play\tps\videostore>play
...
> Type "help play" or "license" for more information.

[videostore] $ help play
Welcome to Play 2.2.1!
These commands are available:
-----------------------------
classpath                  Display the project classpath.
clean                      Clean all generated files.
compile                    Compile the current application.
console                    Launch the interactive Scala console (use :quit to exit).
dependencies               Display the dependencies summary.
dist                       Construct standalone application package.
exit                       Exit the console.
h2-browser                 Launch the H2 Web browser.
license                    Display licensing informations.
package                    Package your application as a JAR.
play-version               Display the Play version.
publish                    Publish your application in a remote repository.
publish-local              Publish your application in the local repository.
reload                     Reload the current application build file.
run <port>                 Run the current application in DEV mode.
test                       Run Junit tests and/or Specs from the command line
eclipse                    generate eclipse project file
idea                       generate Intellij IDEA project file
sh <command to run>        execute a shell command
start <port>               Start the current application in another JVM in PROD mode.
update                     Update application dependencies.
```

We will generate the necessary ".project" file for eclipse.
While staying in the console, run the command "eclipse" (on previous versions of the framework it was "eclipsify").

```
[videostore] $ eclipse
[info] About to create Eclipse project files for your project(s).
[info] Successfully created Eclipse project files for project(s):
[info] videostore
[videostore] $
```

NB: All command can be executed either with the play script, or in the play console.
For instance type "eclipse" in the play command, or directly type "play eclipse" in the terminal.
You could therefore run the following command to have the same result previous command.
```
D:\play\tps\videostore>play eclipse
```

### Import the project in eclipse to see the project structure.

Check that your project has the following structure:
```
app                      → Application sources
 └ assets                → Compiled asset sources
    └ stylesheets        → Typically LESS CSS sources
    └ javascripts        → Typically CoffeeScript sources
 └ controllers           → Application controllers
 └ models                → Application business layer
 └ views                 → Templates
build.sbt                → Application build script
conf                     → Configurations files and other non-compiled resources (on classpath)
 └ application.conf      → Main configuration file
 └ routes                → Routes definition
public                   → Public assets
 └ stylesheets           → CSS files
 └ javascripts           → Javascript files
 └ images                → Image files
project                  → sbt configuration files
 └ build.properties      → Marker for sbt project
 └ plugins.sbt           → sbt plugins including the declaration for Play itself
lib                      → Unmanaged libraries dependencies
logs                     → Standard logs folder
 └ application.log       → Default log file
target                   → Generated stuff
 └ scala-2.10.0            
    └ cache              
    └ classes            → Compiled class files
    └ classes_managed    → Managed class files (templates, ...)
    └ resource_managed   → Managed resources (less, ...)
    └ src_managed        → Generated sources (templates, ...)
test                     → source folder for unit or functional tests
```

### The "app" directory 
This is the directory of the application that contains all Java and Scala sources, templates and assets compiled.
According to MVC architectural pattern, there are three folders:
- app/controllers: controllers, orchestrating the call to the data and displaying in the view
- app/models: the data model
- app/views: views, presenting the data to users

### The "public" directory
Resources stored in the public folder are static files served by the web server (Netty).
This directory is divided into three standard subdirectories for images, CSS and JavaScript files. Keep this organization!
In all Play application!, the ```/public``` directory is associated with the url path ```/assets```.

### The "conf" directory
The "conf" directory contains the configuration files of the application. There are two main configuration files:
- ```application.conf```, the main configuration file of the application, which contains the standard configuration parameters.
- ```routes```, URL mapping to controller's actions definition file.

If you need to add configuration options that are specific to your application, you can add the ```application.conf``` file or use another config file which then use the "include" statement.

For example: ```include "mail"``` at the end of ```application.conf``` file with the file ```mail.conf``` in the "/conf" directory.

If an external library (a jar) needs a specific configuration file (log4j , hibernate , for example), you should put it in the "/conf" directory as it is in the CLASSPATH.

Reminder: CLASSPATH is used by the compiler and the Java virtual machine to find classes or resources needed by a program.
In practice, when Java needs a class or a file (resource), it will scan the classpath (in order of declaration) and will look through each path in the classpath for this resource.
Each element of the CLASSPATH is a root, and classes are searched in subdirectories corresponding to the name of their packages.
More info: http://java.developpez.com/faq/java/?page=langage#LANGUAGE_CLASSPATH

### The "lib" directory
The lib directory is optional and contains dependencies unmanaged libraries by Play ! , Ie all the JAR files we need more libraries proposed by the Framework. By removing the JAR files in the "lib" and they will be automatically added to the classpath directory.

### The "project" directory
The "project" directory contains elements of the application build:
- plugins.sbt defines which sbt repository is used by the project, as "Maven" does.
- build.properties contains the version of sbt to use for the build.

### The "target" directory
The "target" directory contains everything that is generated by the Play framework !
- classes: contains all the Java or Scala compiled classes.
- classes_managed: contains only the classes that are managed by Play! ("routes" file for example, which is translated by the Scala framework).
- resource_managed: contains the generated resources such as CSS, CoffeeScript, Javascript or LESS files.
- src_managed: includes eg templates translated into Scala.

### The "build.sbt"
This is the "main file" of your application.
It includes :
- the name of your project
- the version of your project
- modules of the framework used by your project

Next tutorial
====

Go to [TP03](https://github.com/fmaturel/PlayTraining/tree/master/play_tp03)
