package dashbase.meta;

import bnfgenast.bnf.BnfCom;
import dashbase.env.Context;
import dashbase.utils.ObjectHelper;
import lombok.Getter;
import tools.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dashbase.meta.BnfHelper.cache;
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
            tokensBindMethods.put(subscriberMethod.key(), dependency);
        }

        bindMethods.addAll(subscriberMethods);
    }

    public void sortMapToTree() {
        for (BindMethod subscriberMethod : bindMethods) {
            String key = subscriberMethod.key();

            switch (subscriberMethod.getMode()) {
                case WRAPPER: {
                    this.wrapperBindMethod = subscriberMethod;
                    this.wrapperBindDependency = tokensBindMethods.get(subscriberMethod.key());
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
                        if (prefix.equals("wrapper")) {
                            prefix += "[" + GrammarMode.WRAPPER.name() + "]";
                        } else {
                            prefix += "[" + GrammarMode.OBJECT.name() + "]";
                        }
                        dependencies[i] = ObjectHelper.requireNonNull(tokensBindMethods.get(prefix), "prefix dependency null");
                        dependencies[i].getDependencies().put(key, tokensBindMethods.get(key));
                    }

                    break;
                }
            }
        }
    }

    public BnfCom generate() {
        sortMapToTree();
        BnfCom bnfCom = wrapperBindDependency.create();

        for (BindMethod bindMethod : bindMethods) {
            if (!TextUtils.isEmpty(bindMethod.getInsert()) && cache.containsKey(bindMethod.key())) {
                BnfCom bind = cache.get(bindMethod.key());
                BnfCom insert = cache.get(bindMethod.getInsert() + "[" + GrammarMode.OBJECT.name() + "]");

                bind.or(insert);
            }
        }

        return bnfCom;
    }

    public Context context() {
        Context context = new Context();
        context.addBindMethods(tokensBindMethods);
        return context;
    }
}
