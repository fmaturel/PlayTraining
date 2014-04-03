import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.List;

import models.Acteur;
import models.Film;
import models.Film.FilmBuilder;
import models.Genre;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import play.Logger;
import play.db.jpa.JPA;
import play.libs.F.Function0;
import play.test.FakeApplication;
import play.test.Helpers;

public class IntegrationTest {

    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        try {
            app = Helpers.fakeApplication(inMemoryDatabase("IntegrationTest"));
            start(app);
        } catch (Exception e) {
            Logger.error("Unable to start app: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testPersistence() throws Throwable {

        JPA.withTransaction(new Function0<Void>() {

            @Override
            public Void apply() throws Throwable {
                // Crée un film
                Film film = new FilmBuilder().withTitre("La grande vadrouille") //
                        .withGenre(Genre.COMEDIE) //
                        .withNombreExemplaire(10) //
                        .withActeurPrincipal(new Acteur("De Funes", "Louis")) //
                        .withActeurSecondaire(new Acteur("Bourvil", "André")) //
                        .build();

                // Sauvegarde ce film
                film.save();

                // Récupère tous les films
                List<Film> films = Film.findAll();

                // Vérifie que le film sauvegardé est bien présent
                assertNotNull(films);
                assertEquals(1, films.size());
                assertNotNull(films.get(0));
                assertEquals("La grande vadrouille", films.get(0).titre);

                return null;
            }
        });
    }

    @AfterClass
    public static void stopApp() {
        if (app != null) {
            Helpers.stop(app);
        }
    }

}
