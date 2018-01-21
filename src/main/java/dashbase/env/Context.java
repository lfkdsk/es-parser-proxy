package dashbase.env;

import dashbase.meta.Dependency;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Context {

    @Getter
    private final Map<String, Dependency> evals = new HashMap<>();

    public void addBindMethods(Map<String, Dependency> evals) {
        this.evals.putAll(evals);
    }
}
