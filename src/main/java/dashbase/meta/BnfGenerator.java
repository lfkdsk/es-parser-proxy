package dashbase.meta;

import bnfgenast.bnf.BnfCom;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.array.AstArrayProperty;
import dashbase.ast.literal.StringLiteral;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.primary.AstPrimaryProperty;
import dashbase.ast.property.AstAllTypeProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.ast.property.Property;
import dashbase.env.Context;
import dashbase.rules.QueryGrammar;
import dashbase.utils.ObjectHelper;
import lombok.Getter;
import tools.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.bnf.BnfCom.wrapper;

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
                case ALL:
                case OBJECT: {

                    String[] prefixes = subscriberMethod.getPrefix();

                    // search prefix dependents
                    Dependency[] dependencies = new Dependency[prefixes.length];
                    for (int i = 0; i < prefixes.length; i++) {
                        String prefix = prefixes[i];
                        Dependency dependency;
                        if (primaryMethods.containsKey(prefix)) {
                            dependency = primaryMethods.get(prefix);
                        } else if (arrayMethods.containsKey(prefix)) {
                            dependency = arrayMethods.get(prefix);
                        } else if (objectMethods.containsKey(prefix)) {
                            dependency = objectMethods.get(prefix);
                        } else {
                            dependency = allMethods.get(prefix);
                        }

                        if (prefix.equals("wrapper")) {
                            dependency = wrapperBindDependency;
                        }

                        dependencies[i] = ObjectHelper.requireNonNull(dependency, "prefix dependency null");
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
                bind.of(insert);
            }
        }

        return bnfCom;
    }

    public Context context() {
        return new Context();
    }

    ///////////////////////////////////////////////////////////////////////////
    // STATIC METHOD
    ///////////////////////////////////////////////////////////////////////////

    private static final QueryGrammar staticGrammar = new QueryGrammar();
    private static final Map<String, BnfCom> cache = new HashMap<>();

    private static final Map<String, Dependency> allMethods = new HashMap<>();
    private static final Map<String, Dependency> primaryMethods = new HashMap<>();
    private static final Map<String, Dependency> objectMethods = new HashMap<>();
    private static final Map<String, Dependency> arrayMethods = new HashMap<>();

    private static <T extends Property> Consumer<T> createConsumer(Dependency dependency) {
        return astNodes -> astNodes.setCallback((astNodes1, context) -> {
            try {
                dependency.getBindMethod()
                          .getMethod()
                          .invoke(dependency.getBindMethod().getInstance(), astNodes1, context);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private static BnfCom primaryProperty(PrimaryDep dep) {
        return rule(AstPrimaryProperty.class).name(dep.getBindMethod().getName())
                                             .literal(StringLiteral.class, (dep.getBindMethod().getName()))
                                             .sep(":")
                                             .ast(staticGrammar.getPrimary())
                                             .consume(createConsumer(primaryMethods.get(dep.getBindMethod().getName())));
    }

    private static BnfCom objectProperty(Dependency dep, BnfCom object) {
        return rule(AstObjectProperty.class).name(dep.getBindMethod().getName())
                                            .literal(StringLiteral.class, (dep.getBindMethod().getName()))
                                            .sep(":")
                                            .ast(object)
                                            .consume(createConsumer(objectMethods.get(dep.getBindMethod().getName())));
    }

    private static BnfCom arrayProperty(ArrayDep dep) {
        return rule(AstArrayProperty.class).name(dep.getBindMethod().getName())
                                           .literal(StringLiteral.class, (dep.getBindMethod().getName()))
                                           .sep(":")
                                           .ast(staticGrammar.getArray())
                                           .consume(createConsumer(arrayMethods.get(dep.getBindMethod().getName())));
    }

    private static BnfCom allProperty(AllDep dep) {
        return rule(AstAllTypeProperty.class).name(dep.getBindMethod().getName())
                                             .literal(StringLiteral.class, (dep.getBindMethod().getName()))
                                             .sep(":")
                                             .ast(staticGrammar.getValue())
                                             .consume(createConsumer(allMethods.get(dep.getBindMethod().getName())));
    }

    private static BnfCom object(BnfCom properties) {
        return rule(AstObject.class).sep("{")
                                    .maybe(properties)
                                    .sep("}");
    }


    private static BnfCom properties(List<BnfCom> properties) {
        BnfCom rule = rule(AstPropertyList.class);
        properties.add(staticGrammar.getProperty());
        BnfCom property = wrapper().or(properties.toArray(new BnfCom[0]));

        return rule.ast(property).repeat(
                rule().sep(",").repeat(property)
        );
    }

    ///////////////////////////////////////////////////////////////////////////
    // DEPENDENCY CLASS
    ///////////////////////////////////////////////////////////////////////////

    private static class PrimaryDep extends Dependency {

        PrimaryDep(BindMethod bindMethod) {
            super(bindMethod);
        }

        @Override
        public BnfCom create() {
            return primaryProperty(this);
        }
    }

    private static class ObjectDep extends Dependency {

        ObjectDep(BindMethod bindMethod) {
            super(bindMethod);
        }

        public BnfCom create() {
            BnfCom properties = properties(getDependencies().values()
                                                            .stream()
                                                            .map(Dependency::create)
                                                            .collect(Collectors.toList()));

            BnfCom object = object(properties);
            cache.put(getBindMethod().key(), object);

            return objectProperty(this, object);
        }
    }

    private static class Wrapper extends Dependency {
        Wrapper(BindMethod method) {
            super(method);
        }

        @Override
        public BnfCom create() {
            BnfCom properties = properties(getDependencies().values()
                                                            .stream()
                                                            .map(Dependency::create)
                                                            .collect(Collectors.toList()));

            return rule(AstQueryProgram.class).ast(object(properties));
        }
    }

    private static class ArrayDep extends Dependency {
        ArrayDep(BindMethod method) {
            super(method);
        }

        @Override
        public BnfCom create() {
            return arrayProperty(this);
        }
    }

    private static class AllDep extends Dependency {

        AllDep(BindMethod bindMethod) {
            super(bindMethod);
        }

        @Override
        public BnfCom create() {
            return allProperty(this);
        }
    }

    private Dependency dependency(BindMethod method) {
        Dependency dependency;
        switch (method.getMode()) {
            default:
            case WRAPPER: {
                return wrapperBindDependency = new Wrapper(method);
            }
            case PRIMARY: {
                dependency = new PrimaryDep(method);
                primaryMethods.put(method.getName(), dependency);
                return dependency;
            }
            case OBJECT: {
                dependency = new ObjectDep(method);
                objectMethods.put(method.getName(), dependency);
                return dependency;
            }
            case ARRAY: {
                dependency = new ArrayDep(method);
                arrayMethods.put(method.getName(), dependency);
                return dependency;
            }
            case ALL: {
                dependency = new AllDep(method);
                allMethods.put(method.getName(), dependency);
                return dependency;
            }
        }
    }
}
