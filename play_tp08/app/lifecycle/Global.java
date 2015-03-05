package lifecycle;

import models.Stock;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {

        if (!Play.isTest()) {
            Stock.populate();
        }

        Logger.info("L'application a démarré");
    }

    @Override
    public void onStop(Application app) {
        Logger.info("L'application s'arrête...");
    }

    @Override
    public Promise<Result> onError(RequestHeader request, Throwable t) {
        return Promise.<Result>pure(play.mvc.Results.internalServerError(views.html.error.render(t)));
    }

}
