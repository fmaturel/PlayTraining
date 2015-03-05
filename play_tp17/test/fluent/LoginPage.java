package fluent;

import org.fluentlenium.core.FluentPage;
import org.openqa.selenium.WebDriver;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Illustration of the Page Object Pattern in Fluentlenium.
 */
public class LoginPage extends FluentPage {

    private String url;

    /**
     * Create the LoginPage.
     *
     * @param webDriver The driver.
     * @param port      The port.
     */
    public LoginPage(WebDriver webDriver, int port, String path) {
        super(webDriver);
        this.url = "http://localhost:" + port + path;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void isAt() {
        assertThat(title()).isEqualTo("Identifiez-vous sur le BackOffice");
    }

    /**
     * Set the form to the passed values, then submit it.
     *
     * @param email    The form email data.
     * @param password The form password value.
     */
    public void submitForm(String email, String password) {
        fill("#email").with(email);
        fill("#password").with(password);
        submit("#submit");
    }

}