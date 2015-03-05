import org.junit.Test;
import play.api.mvc.RequestHeader;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.twirl.api.Html;

import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

/**
 * Simple (JUnit) tests that can call all parts of a play app.<br>
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() throws InstantiationException, IllegalAccessException {
        Request requestMock = mock(Request.class);

        Context.current.set(new Context(1l, mock(RequestHeader.class), requestMock, //
                Collections.<String, String>emptyMap(), // sessionData
                Collections.<String, String>emptyMap(), // flashData
                Collections.<String, Object>emptyMap()));

        when(requestMock.username()).thenReturn("nom_de_test");

        Html html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
        assertThat(contentAsString(html)).contains("Bonjour nom_de_test!");
    }

}
