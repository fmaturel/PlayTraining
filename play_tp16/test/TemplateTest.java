import org.junit.Test;
import play.twirl.api.Html;
import views.html.index;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

public class TemplateTest {

    @Test
    public void renderTemplate() {
        Html html = index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }

}
