package controllers.bo;

import play.mvc.Http.Context;
import play.mvc.Result;
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