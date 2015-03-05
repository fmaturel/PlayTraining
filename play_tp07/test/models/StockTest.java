package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Logger;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class StockTest {

    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        try {
            app = Helpers.fakeApplication();
        } catch (Exception e) {
            Logger.error("Unable to start app: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testStockPopulationShouldContains3Films() {

        HashSet<Film> liste = Stock.populate();

        for (Film film : liste) {
            Logger.debug("Film en stock: {}", film);
        }

        assertEquals(3, liste.size());
    }

    @AfterClass
    public static void stopApp() {
        if (app != null) {
            Helpers.stop(app);
        }
    }
}
