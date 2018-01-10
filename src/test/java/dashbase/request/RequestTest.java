package dashbase.request;

import com.lfkdsk.justel.utils.logger.Logger;
import org.junit.Test;
import sun.rmi.runtime.Log;


import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void testRequestJson() {
        Request request = new Request();
        Logger.init();
        Logger.v(request.toJson());
    }
}