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
        return redirect(routes.UserLogin.login());
    }
}
