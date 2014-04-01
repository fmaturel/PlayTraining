package lifecycle;

import models.Stock;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;

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

}
