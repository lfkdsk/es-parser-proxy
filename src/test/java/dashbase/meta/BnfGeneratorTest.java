package dashbase.meta;

import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.env.Context;
import dashbase.lexer.JustLexer;
import logger.Logger;
import org.junit.Assert;
import org.junit.Test;

public class BnfGeneratorTest {

    public static class FinderDemo {

        @Bind(name = "wrapper", mode = GrammarMode.WRAPPER)
        public void bind(AstQueryProgram property, Context context) {

        }

        @Bind(name = "query", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test(AstObjectProperty property, Context context) {

        }
    }

    @Test
    public void testBnfDependencySorted() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(FinderDemo.class);

        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getTokensBindMethods().size(), 2);


        generator.sortMapToTree();
        Assert.assertEquals(generator.getTokensBindMethods()
                                     .get("wrapper[WRAPPER]")
                                     .getDependencies()
                                     .get("query[OBJECT]")
                                     .getBindMethod()
                                     .getMode(), GrammarMode.OBJECT);
    }

    public static class FinderDemo2 {
        @Bind(name = "wrapper", mode = GrammarMode.WRAPPER)
        public void bind(AstQueryProgram property, Context context) {

        }

        @Bind(name = "query", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test(AstObjectProperty property, Context context) {

        }

        @Bind(name = "filter", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test1(AstObjectProperty property, Context context) {

        }
    }


    @Test
    public void testGenerate() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(FinderDemo2.class);

        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getTokensBindMethods().size(), 3);


        JsonObject object = new JsonObject();
        object.add("filter", new JsonObject());
        object.add("query", new JsonObject());

        Lexer lexer = new JustLexer(object.toString());
        lexer.reserved(("query"));
        lexer.reserved(("filter"));

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) (bnfCom.parse(lexer));

        Assert.assertNotNull(program);

        AstObject object1 = program.object();
        AstPropertyList list = object1.propertyList();
        Assert.assertEquals(2, list.childCount());
    }

    public static class FinderDemo3 {
        public static StringBuilder result = new StringBuilder();

        @Bind(name = "wrapper", mode = GrammarMode.WRAPPER)
        public void bind(AstQueryProgram property, Context context) {
            System.out.println("wrapper");
            result.append("wrapper");
        }

        @Bind(name = "query", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test(AstObjectProperty property, Context context) {
            System.out.println("query");
            result.append("query");
        }

        @Bind(name = "match_all", mode = GrammarMode.OBJECT, prefix = {"query"})
        public void test1(AstObjectProperty property, Context context) {
            System.out.println("match_all");
            result.append("match_all");
        }

        @Bind(name = "bool", mode = GrammarMode.OBJECT, prefix = "query")
        public void test2(AstObjectProperty property, Context context) {
            System.out.println("bool");
            result.append("bool");
        }
        @Bind(name = "must", mode = GrammarMode.OBJECT, prefix = "bool", insert = "query")
        public void must(AstObjectProperty property, Context context) {
            System.out.println("must");
            result.append("must");
        }
    }

    @Test
    public void testWrongPositionGrammar() {
//        String json = "{\n" +
//                "   \"query\":{\n" +
//                "      \"bool\":{\n" +
//                "         \"must\":{\n" +
//                "\n" +      "\"match_all\":{}"+
//                "         }\n" +
//                "      }\n" +
//                "   },\n" +
//                "   \"bool\":{\n" +
//                "      \"match_all\":{\n" +
//                "\n" +
//                "      }\n" +
//                "   }\n" +
//                "}";

        BnfGenerator generator = new BnfGenerator();
        FinderDemo3 demo3 = new FinderDemo3();
        generator.register(demo3);

        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getTokensBindMethods().size(), 5);

        JsonObject wrapper = new JsonObject();

        JsonObject query = new JsonObject();
        JsonObject matchall = new JsonObject();
        JsonObject bool = new JsonObject();
        JsonObject must = new JsonObject();

        must.add("match_all", matchall);
        bool.add("must", must);
        query.add("bool", bool);
        wrapper.add("query", query);
        wrapper.add("bool", bool.deepCopy());

        Logger.init();
        Logger.v(new GsonBuilder().setPrettyPrinting().create().toJson(wrapper));

        Lexer lexer = new JustLexer(wrapper.toString());
        lexer.reserved(("query"));
        lexer.reserved(("bool"));
        lexer.reserved(("match_all"));
        lexer.reserved(("must"));

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) (bnfCom.parse(lexer));

        Assert.assertNotNull(program);
        program.eval(generator.context());
        System.out.println(FinderDemo3.result);
        Assert.assertEquals("match_allmustboolquery", FinderDemo3.result.toString());
    }


}