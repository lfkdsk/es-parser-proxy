package dashbase.request;

import com.sun.org.apache.regexp.internal.RE;
import lombok.NonNull;

public class Requests {

    public static Request queryAllFields(@NonNull Request request) {
        request.getFields().clear();
        request.getFields().add("*");

        return request;
    }
}
