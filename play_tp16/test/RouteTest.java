import org.junit.Test;
import org.junit.Ignore;
import play.libs.F.Promise;
import play.mvc.Http.Status;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

@Ignore
public class RouteTest {

    @Test
    public void badRoute() throws Exception {
        Result result = route(fakeRequest(GET, "/badurl"));
        assertThat(result.toScala().header().status()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void rootRoute() {
        Result result = route(fakeRequest(GET, "/bo"));
        assertThat(result).isNotNull();
    }
}
