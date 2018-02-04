package dashbase.meta;

import bnfgenast.bnf.BnfCom;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.array.AstArrayProperty;
import dashbase.ast.literal.StringLiteral;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.primary.AstPrimaryProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.rules.QueryGrammar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static bnfgenast.bnf.BnfCom.rule;
import static bnfgenast.utils.TextUtils.w;


class BnfHelper {

    public static Dependency dependency(BindMethod method) {
        switch (method.getMode()) {
            default:
            case WRAPPER: {
                return new Wrapper(method);
            }
            case PRIMARY: {
                return new PrimaryDep(method);
            }
            case OBJECT: {
                return new ObjectDep(method);
            }
            case ARRAY: {
                return new ArrayDep(method);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //   BnfCom propertyList =
    //        rule(AstPropertyList.class)
    //        .option(rule(AstObjectProperty.class).literal(w("query")).sep(":").ast(grammar.getObject()).maybe(","))
    //        .option(rule(AstObjectProperty.class).literal(w("lfkdsk")).sep(":").ast(grammar.getObject()).maybe(","))
    //        .option(grammar.getProperty())
    //        .repeat(
    //             rule().sep(",").repeat(grammar.getProperty())
    //        );
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Static Grammar
     */
    private static final QueryGrammar staticGrammar = new QueryGrammar();

    public static final Map<String, BnfCom> cache = new HashMap<>();

    public static BnfCom primaryProperty(PrimaryDep dep) {
        return rule(AstPrimaryProperty.class).name(dep.getBindMethod().getName())
                                             .literal(StringLiteral.class, w(dep.getBindMethod().getName()))
                                             .sep(":")
                                             .ast(staticGrammar.getPrimary());
    }

    public static BnfCom objectProperty(Dependency dep, BnfCom object) {
        return rule(AstObjectProperty.class).name(dep.getBindMethod().getName())
                                            .literal(StringLiteral.class, w(dep.getBindMethod().getName()))
                                            .sep(":")
                                            .ast(object);
    }

    public static BnfCom arrayProperty(ArrayDep dep) {
        return rule(AstArrayProperty.class).name(dep.getBindMethod().getName())
                                           .literal(StringLiteral.class, w(dep.getBindMethod().getName()))
                                           .sep(":")
                                           .ast(staticGrammar.getArray());
    }

    public static BnfCom object(BnfCom properties) {
        return rule(AstObject.class).sep("{")
                                    .maybe(properties)
                                    .sep("}");
    }


    public static BnfCom properties(List<BnfCom> properties) {
        BnfCom rule = rule(AstPropertyList.class);

        for (BnfCom property : properties) {
            property.maybe(",");
        }

        properties.add(staticGrammar.getPropertyList());

        return rule.or(properties.toArray(new BnfCom[0]));
    }

    public static class PrimaryDep extends Dependency {

        public PrimaryDep(BindMethod bindMethod) {
            super(bindMethod);
        }

        @Override
        public BnfCom create() {
            return primaryProperty(this);
        }
    }

    public static class ObjectDep extends Dependency {

        public ObjectDep(BindMethod bindMethod) {
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
        public Wrapper(BindMethod method) {
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
        public ArrayDep(BindMethod method) {
            super(method);
        }

        @Override
        public BnfCom create() {
            return arrayProperty(this);
        }
    }
}
