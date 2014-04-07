package fluent;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.User;

import org.junit.Test;

import play.Logger;
import play.db.jpa.JPA;
import play.libs.F.Callback;
import play.libs.F.Function0;
import play.test.TestBrowser;

/**
 * Sample "integration" test, using the functioning webapp.
 * 
 * @author Philip Johnson
 */
public class FonctionalFluentTest {

    /** The port to be used for testing. */
    private final int port = 3333;

    /**
     * Sample test that submits a form and then checks that form data was echoed to page.
     */
    @Test
    public void test() {

        running(testServer(port, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) throws Throwable {

                final User admin = new User();
                admin.email = "admin@play.com";
                admin.name = "administrateur";
                admin.password = "laformationplay";
                JPA.withTransaction(new Function0<Void>() {
                    @Override
                    public Void apply() throws Throwable {
                        admin.save();
                        return null;
                    }
                });

                LoginPage loginPage = new LoginPage(browser.getDriver(), port, "/bo");
                browser.goTo(loginPage);

                Logger.of("tests").info(browser.pageSource());

                loginPage.isAt();
                loginPage.submitForm(admin.email, admin.password);

                Logger.of("tests").info(browser.pageSource());

                assertThat(browser.pageSource()).contains("Bonjour " + admin.email);
            }
        });
    }
}
