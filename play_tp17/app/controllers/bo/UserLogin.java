package controllers.bo;

import models.User;
import play.cache.Cached;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import utils.PerfLogger;
import views.html.bo.login;

public class UserLogin extends Controller {
    
    /**
     * Cache duration in seconds: 1 month
     */
    private static final int LONG_DURATION = 3600 * 24 * 30;

    public static class Login {

        @Constraints.Required
        private String email;
        @Constraints.Required
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "email ou mot de passe incorrect";
            }
            return null;
        }
    }

    /**
     * Get the login form.
     */
    @Cached(key = "bo.login", duration = LONG_DURATION)
    public static Result login() {
        PerfLogger perf = PerfLogger.start("http.backoffice.login");
        Result result = ok(login.render(Form.form(Login.class)));
        perf.stop();
        return result;
    }

    /**
     * Authenticate the user from the login form.
     */
    @play.db.jpa.Transactional(readOnly = true)
    public static Result authenticate() {
        PerfLogger perf = PerfLogger.start("http.backoffice.authenticate");
        Result result;
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            result = badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            result = redirect(routes.Backoffice.index());
        }
        perf.stop();
        return result;
    }

    public static Result logout() {
        PerfLogger perf = PerfLogger.start("http.backoffice.logout");
        session().clear();
        flash("success", "You've been logged out");
        Result result = redirect(routes.UserLogin.login());
        perf.stop();
        return result;
    }
}
