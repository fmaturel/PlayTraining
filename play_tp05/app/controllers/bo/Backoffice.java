package controllers.bo;

import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import utils.PerfLogger;
import views.html.bo.action;
import views.html.bo.index;

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
     * Display the list of film.
     */
    public static Result list() {
        return ok(action.render("Lister les films", null));
    }

    /**
     * Display the 'edit form' of a existing Film.
     *
     * @param id Id of the film to edit
     */
    public static Result edit(Long id) {
        return ok(action.render("Editer un film", id));
    }

    /**
     * Handle the 'edit form' submission
     *
     * @param id Id of the film to edit
     */
    public static Result update(Long id) {
        return ok(action.render("Modifier un film", id));
    }

    /**
     * Display the 'new film form'.
     */
    public static Result create() {
        return ok(action.render("Créer un film", null));
    }

    /**
     * Handle the 'new film form' submission
     */
    public static Result save() {
        return ok(action.render("Enregistrer un film", null));
    }

    /**
     * Handle film deletion
     */
    public static Result delete(Long id) {
        return ok(action.render("Supprimer un film", id));
    }
}
