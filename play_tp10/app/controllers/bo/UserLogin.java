package controllers.bo;

import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.bo.login;

public class UserLogin extends Controller {

    public static class Login {

        @Constraints.Required
        public String email;
        @Constraints.Required
        public String password;

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
    public static Result login() {
        return ok(login.render(Form.form(Login.class)));
    }

    /**
     * Authenticate the user from the login form.
     */
    @play.db.jpa.Transactional(readOnly = true)
    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(routes.Backoffice.index());
        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.UserLogin.login()
        );
    }
}
