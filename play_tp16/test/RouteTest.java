import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;

import org.junit.Test;

import play.api.mvc.SimpleResult;
import play.libs.F.Promise;
import play.mvc.Http.Status;
import play.mvc.Result;

public class RouteTest {

    @Test
    public void badRoute() throws Exception {
        SimpleResult result = Promise.wrap(routeAndCall(fakeRequest(GET, "/badurl")).getWrappedResult()).get();
        assertThat(result.header().status()).isEqualTo(Status.NOT_FOUND);
    }

    @Test
    public void rootRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/bo"));
        assertThat(result).isNotNull();
    }
}
