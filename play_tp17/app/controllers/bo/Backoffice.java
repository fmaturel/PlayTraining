package controllers.bo;

import static play.data.Form.form;
import models.Film;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.PerfLogger;
import views.html.bo.createForm;
import views.html.bo.editForm;
import views.html.bo.index;
import views.html.bo.list;
import views.html.bo.notfound;

@Security.Authenticated(controllers.bo.Authenticator.class)
public class Backoffice extends Controller {

    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(routes.Backoffice.list());

    /**
     * Display the welcome page of backoffice.
     */
    public static Result index() {
        PerfLogger perf = PerfLogger.start("http.backoffice.index");
        Result result = ok(index.render());
        perf.stop();
        return result;
    }

    public static Result pageNotFound(String url) {
        return notFound(notfound.render(url));
    }

    public static Result triggerError() {
        throw new RuntimeException("Message");
    }

    /**
     * Display the list of {@link Film}.
     */
    @play.db.jpa.Transactional(readOnly = true)
    public static Result list() {
        PerfLogger perf = PerfLogger.start("http.backoffice.list");
        Result result = ok(list.render(Film.findAll()));
        perf.stop();
        return result;
    }

    /**
     * Display the 'edit form' of a existing Film.
     * 
     * @param id Id of the film to edit
     */
    @play.db.jpa.Transactional(readOnly = true)
    public static Result edit(Long id) {
        PerfLogger perf = PerfLogger.start("http.backoffice.edit[{}]", id);
        Film film = Film.findById(id);
        if (film == null) {
            return notFound("Ressource non trouvée");
        }
        Form<Film> filmForm = form(Film.class).fill(film);
        Result result = ok(editForm.render(id, filmForm));
        perf.stop();
        return result;
    }

    /**
     * Handle the 'edit form' submission
     * 
     * @param id Id of the film to edit
     */
    @play.db.jpa.Transactional
    public static Result update(Long id) {
        PerfLogger perf = PerfLogger.start("http.backoffice.update[{}]", id);
        Form<Film> filmForm = form(Film.class).bindFromRequest();
        if (filmForm.hasErrors()) {
            return badRequest(editForm.render(id, filmForm));
        }
        filmForm.get().update(id);
        flash("success", "Le film " + filmForm.get().titre + " a été mis à jour");
        perf.stop();
        return list();
    }

    /**
     * Display the 'new film form'.
     */
    @play.db.jpa.Transactional(readOnly = true)
    public static Result create() {
        PerfLogger perf = PerfLogger.start("http.backoffice.create");
        Form<Film> filmForm = form(Film.class);
        Result result = ok(createForm.render(filmForm));
        perf.stop();
        return result;
    }

    /**
     * Handle the 'new film form' submission
     */
    @play.db.jpa.Transactional
    public static Result save() {
        PerfLogger perf = PerfLogger.start("http.backoffice.save");
        Form<Film> filmForm = form(Film.class).bindFromRequest();
        if (filmForm.hasErrors()) {
            return badRequest(createForm.render(filmForm));
        }
        filmForm.get().save();
        flash("success", "Le film " + filmForm.get().titre + " a été créé!");
        perf.stop();
        return list();
    }

    /**
     * Handle film deletion
     */
    @play.db.jpa.Transactional
    public static Result delete(Long id) {
        PerfLogger perf = PerfLogger.start("http.backoffice.delete[{}]", id);
        Film film = Film.findById(id);
        if (film == null) {
            return notFound("Ressource non trouvée");
        }
        film.delete();
        flash("success", "Le film " + film.titre + " a été supprimé");
        perf.stop();
        return list();
    }

}
