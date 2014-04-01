package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.notfound;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Play ▶ Films"));
    }

    public static Result pageNotFound(String url) {
        return notFound(notfound.render(url));
    }
}
