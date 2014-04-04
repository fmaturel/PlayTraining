TP01: Install Play! and the appropriate tools
====

# Step 1: Getting and Installing programs used in the course

## Retrieving binaries

Follow the trainer's to retrieve the binaries of following programs:

| Program | Version | Use |
| :------------ |:---------------:| -----:|
| Java JDK 6 or 7 | 7u51 | Java Development Kit containing common classes required for any development in Java |
| Play! Framework 2 | 2.2.2 (02/2014) | This is the Java Web Framework studied during the course! Source: http://www.playframework.com |
| Eclipse Kepler with Scala | 3.0.2 | The Eclipse IDE enhanced with a Scala editor and plugin for Play! Framework. Source: http://scala-ide.org |

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

Go to [TP02](https://github.com/fmaturel/PlayTraining/tree/master/play_tp03)
