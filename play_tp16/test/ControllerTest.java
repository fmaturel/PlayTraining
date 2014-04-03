import static controllers.routes.ref.Frontoffice;
import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;
import static play.test.Helpers.status;
import models.Acteur;
import models.Film;
import models.Film.FilmBuilder;
import models.Genre;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.Logger;
import play.db.jpa.JPA;
import play.libs.F.Function0;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;

public class ControllerTest {

    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        try {
            app = Helpers.fakeApplication(inMemoryDatabase("ControllerTest"));
            start(app);
        } catch (Exception e) {
            Logger.error("Unable to start app: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Throwable {
        JPA.withTransaction(new Function0<Void>() {

            @Override
            public Void apply() {
                // Crée un film
                Film film = new FilmBuilder().withTitre("La grande vadrouille") //
                        .withGenre(Genre.COMEDIE) //
                        .withNombreExemplaire(10) //
                        .withActeurPrincipal(new Acteur("De Funes", "Louis")) //
                        .withActeurSecondaire(new Acteur("Bourvil", "André")) //
                        .build();

                // Sauvegarde ce film
                film.save();
                return null;
            }
        });
    }

    @Test
    public void testFrontOfficeFilmList() {

        Result result = callAction(Frontoffice.list(Genre.COMEDIE.toString(), 4),//
                fakeRequest().withHeader("Content-Type", "application/json"));

        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("application/json");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).isEqualTo("[{\"id\":1,"//
                + "\"titre\":\"La grande vadrouille\","//
                + "\"genre\":\"COMEDIE\","//
                + "\"nombreExemplaire\":10,"//
                + "\"acteurPrincipal\":{\"id\":1,\"nom\":\"De Funes\",\"prenom\":\"Louis\"},"//
                + "\"acteurSecondaires\":[{\"id\":2,\"nom\":\"Bourvil\",\"prenom\":\"André\"}]}]"//
        );
    }

    @AfterClass
    public static void stopApp() {
        if (app != null) {
            Helpers.stop(app);
        }
    }
}
