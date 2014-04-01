package controllers.bo;

import play.mvc.*;
import play.mvc.Http.*;

import play.mvc.Security;

public class Authenticator extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("email");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.UserLogin.login());
	}

}