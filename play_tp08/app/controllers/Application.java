package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.notfound;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Formation Play! Framework..."));
    }

    public static Result pageNotFound(String url) {
        return notFound(notfound.render(url));
    }

    public static Result triggerError() {
        throw new RuntimeException("Message");
    }
}
