package lifecycle;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.mvc.EssentialFilter;
import play.filters.gzip.GzipFilter;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.SimpleResult;

public class Global extends GlobalSettings {
    
    @SuppressWarnings("unchecked")
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{GzipFilter.class};
    }

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
		return Promise.<SimpleResult> pure(play.mvc.Results.internalServerError(views.html.bo.error.render(t)));
	}

}
