TP04: Create a RESTful API for backoffice
====

# Step 1: Edit the "routes" file

As part of our DVD inventory management application for online sales (backoffice), we will create a REST API.
It will address following user needs, written as a "user story"

```
As a back office user, I can:
- retrieve a list of existing films in storage
- add a movie with a title, genre and a number of copies available
- edit a movie, title, genre, or the number of copies available
- remove a movie from storage
```

Change the "routes" file of your application to reflect those needs.

For now, the methods associated with each URL will refer simply to a new page to check what action is required.

```
public static Result edit (Long id) {
    ok return (action.render ("Editing a movie", id));
}
```

These methods will be logically placed into a controller located in the package "controllers.bo" and named "Back Office". The Back Office Home to be called "index.scala.html" and will be associated with the URL "/ bo." Finally the view to verify the action asserted will be called "action.scala.html" and check back office will be responsible to pass the value of the action to take at the sight. These views will be logically placed in the "views / bo" directory.
Our application will have the following structure:

```
.
├── controllers
│ ├── Application.java
│ └── bo
│ └── Backoffice.java
└── views
    ├── bo
    │ ├── action.scala.html
    │ └── index.scala.html
    ├── index.scala.html
    └── main.scala.html
```

The template "index.scala.html" will display the following screen: (word doc only)

Finally, the template "action.scala.html" will display the following screen: (word doc only)

Next tutorial
====

Go to [TP05](https://github.com/fmaturel/PlayTraining/tree/master/play_tp05)
