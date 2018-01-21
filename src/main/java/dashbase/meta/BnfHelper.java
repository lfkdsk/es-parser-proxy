package dashbase.meta;

import dashbase.ast.AstQueryProgram;
import dashbase.ast.array.AstArrayProperty;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.primary.AstPrimaryProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.bnf.BnfCom;
import dashbase.rules.QueryGrammar;

import static dashbase.bnf.BnfCom.rule;
import static dashbase.utils.tools.TextUtils.w;

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

    public static BnfCom primaryProperty(PrimaryDep dep) {
        return rule(AstPrimaryProperty.class).literal(w(dep.getBindMethod().getName()))
                                             .sep(":")
                                             .ast(staticGrammar.getPrimary());
    }

    public static BnfCom objectProperty(Dependency dep, BnfCom object) {
        return rule(AstObjectProperty.class).literal(w(dep.getBindMethod().getName()))
                                            .sep(":")
                                            .ast(object);
    }

    public static BnfCom arrayProperty(ArrayDep dep) {
        return rule(AstArrayProperty.class).literal(w(dep.getBindMethod().getName()))
                                           .sep(":")
                                           .ast(staticGrammar.getArray());
    }

    public static BnfCom object(BnfCom properties) {
        return rule(AstObject.class).sep("{")
                                    .maybe(properties)
                                    .sep("}");
    }


    public static BnfCom properties(BnfCom... properties) {
        BnfCom rule = rule(AstPropertyList.class);
        for (BnfCom property : properties) {
            rule = rule.option(property.maybe(","));
        }

        return rule.option(staticGrammar.getProperty())
                   .repeat(
                           rule().sep(",").repeat(staticGrammar.getProperty())
                   );
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
                                                            .toArray(BnfCom[]::new));

            BnfCom object = object(properties);


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
                                                            .toArray(BnfCom[]::new));

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
