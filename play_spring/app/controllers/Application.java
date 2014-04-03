package controllers;

import javax.inject.Inject;

import play.mvc.Controller;
import play.mvc.Result;
import services.HelloService;
import views.html.index;

@org.springframework.stereotype.Controller
public class Application extends Controller {

    @Inject
    private HelloService helloService;

    public Result index() {
        return ok(index.render(helloService.hello()));
    }

}