package dashbase.request;

import dashbase.utils.logger.Logger;
import org.junit.Test;

public class RequestTest {

    @Test
    public void testRequestJson() {
        Request request = new Request();
        Logger.init();
        Logger.v(request.toJson());
    }
}