package controllers.bo;

import models.Film;
import models.Stock;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import utils.PerfLogger;
import views.html.bo.createForm;
import views.html.bo.editForm;
import views.html.bo.index;
import views.html.bo.list;

import static play.data.Form.form;

public class Backoffice extends Controller {

    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(routes.Backoffice.list());

    /**
     * Display the welcome page of backoffice.
     */
    public static Result index() {
        PerfLogger perf = PerfLogger.start();
        Html htmlRendered = index.render("Bienvenue sur le Backoffice de la boutique de vente de DVD en ligne!");
        perf.stop("Rendu de la page de bienvenue du backoffice");
        return ok(htmlRendered);
    }

    /**
     * Display the list of {@link Film}.
     */
    public static Result list() {
        return ok(list.render(Stock.getListeDesFilms()));
    }

    /**
     * Display the 'edit form' of a existing Film.
     *
     * @param id Id of the film to edit
     */
    public static Result edit(Long id) {
        Film film = Stock.getFilm(id);
        if (film == null) {
            return notFound("Ressource non trouvée");
        }
        Form<Film> filmForm = form(Film.class).fill(film);
        return ok(editForm.render(id, filmForm));
    }

    /**
     * Handle the 'edit form' submission
     *
     * @param id Id of the film to edit
     */
    public static Result update(Long id) {
        Form<Film> filmForm = form(Film.class).bindFromRequest();
        if (filmForm.hasErrors()) {
            return badRequest(editForm.render(id, filmForm));
        }
        filmForm.get().update(id);
        flash("success", "Le film " + filmForm.get().titre + " a été mis à jour");
        return GO_HOME;
    }

    /**
     * Display the 'new film form'.
     */
    public static Result create() {
        Form<Film> filmForm = form(Film.class);
        return ok(createForm.render(filmForm));
    }

    /**
     * Handle the 'new film form' submission
     */
    public static Result save() {
        Form<Film> filmForm = form(Film.class).bindFromRequest();
        if (filmForm.hasErrors()) {
            return badRequest(createForm.render(filmForm));
        }
        Stock.getListeDesFilms().add(filmForm.get());
        flash("success", "Le film " + filmForm.get().titre + " a été créé!");
        return GO_HOME;
    }

    /**
     * Handle film deletion
     */
    public static Result delete(Long id) {
        Film film = Stock.removeFilm(id);
        if (film == null) {
            return notFound("Ressource non trouvée");
        }
        flash("success", "Le film " + film.titre + " a été supprimé");
        return GO_HOME;
    }
}
