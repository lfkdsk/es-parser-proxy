package dashbase.meta;

import dashbase.bnf.BnfCom;
import dashbase.utils.ObjectHelper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dashbase.meta.BnfHelper.dependency;

public class BnfGenerator {

    private final BindMethodFinder bindMethodFinder;

    @Getter
    private final Map<String, Dependency> tokensBindMethods;

    @Getter
    private BindMethod wrapperBindMethod;

    @Getter
    private Dependency wrapperBindDependency;

    private List<BindMethod> bindMethods = new ArrayList<>();

    public BnfGenerator() {
        this.bindMethodFinder = new BindMethodFinder();
        this.tokensBindMethods = new HashMap<>();
    }

    public void register(Object subscriber) {
        register(subscriber.getClass());
    }

    public void register(Class<?> subscriberClass) {
        List<BindMethod> subscriberMethods = bindMethodFinder.findBindMethods(subscriberClass);

        for (BindMethod subscriberMethod : subscriberMethods) {
            Dependency dependency = dependency(subscriberMethod);
            tokensBindMethods.put(subscriberMethod.getName(), dependency);
        }

        bindMethods.addAll(subscriberMethods);
    }

    public void sortMapToTree() {
        for (BindMethod subscriberMethod : bindMethods) {
            String name = subscriberMethod.getName();

            switch (subscriberMethod.getMode()) {
                case WRAPPER: {
                    this.wrapperBindMethod = subscriberMethod;
                    this.wrapperBindDependency = tokensBindMethods.get(name);
                    break;
                }
                case PRIMARY:
                case ARRAY:
                case OBJECT: {

                    String[] prefixes = subscriberMethod.getPrefix();

                    // search prefix dependents
                    Dependency[] dependencies = new Dependency[prefixes.length];
                    for (int i = 0; i < prefixes.length; i++) {
                        String prefix = prefixes[i];
                        dependencies[i] = ObjectHelper.requireNonNull(tokensBindMethods.get(prefix), "prefix dependency null");
                        dependencies[i].getDependencies().put(name, tokensBindMethods.get(name));
                    }

                    break;
                }
            }
        }
    }


    public BnfCom generate() {
        sortMapToTree();

        return wrapperBindDependency.create();
    }
}
