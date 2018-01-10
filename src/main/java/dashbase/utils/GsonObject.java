package dashbase.utils;

import com.google.gson.Gson;

public interface GsonObject {

    default String toJson() {
        return new Gson().toJson(this);
    }
}
