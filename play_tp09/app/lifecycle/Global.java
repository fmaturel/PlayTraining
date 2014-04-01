package lifecycle;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.SimpleResult;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("L'application a démarré");
	}

	@Override
	public void onStop(Application app) {
		Logger.info("L'application s'arrête...");
	}

	@Override
	public Promise<SimpleResult> onError(RequestHeader request, Throwable t) {
		return Promise.<SimpleResult> pure(play.mvc.Results.internalServerError(views.html.error.render(t)));
	}

}
