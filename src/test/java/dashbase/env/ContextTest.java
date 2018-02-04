package dashbase.env;

import bnfgenast.bnf.BnfCom;
import bnfgenast.lexer.Lexer;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.lexer.JustLexer;
import dashbase.meta.Bind;
import dashbase.meta.BnfGenerator;
import dashbase.meta.GrammarMode;
import logger.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ContextTest {

    public static class FinderDemo2 {

        @Bind(name = "wrapper", mode = GrammarMode.WRAPPER)
        public void bind(AstQueryProgram property, Context context) {

        }

        @Bind(name = "query", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test(AstObjectProperty property, Context context) {

        }

        @Bind(name = "match_all", mode = GrammarMode.OBJECT, prefix = {"query"})
        public void test1(AstObjectProperty property, Context context) {

        }
    }


    @Test
    public void testContextEval() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(FinderDemo2.class);

        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getTokensBindMethods().size(), 3);


        JsonObject object = new JsonObject();
        JsonObject queryJsonObj = new JsonObject();

        queryJsonObj.add("match_all", new JsonObject());
        object.add("query", queryJsonObj);

        Lexer lexer = new JustLexer(object.toString());
        lexer.reserved(("query"));
        lexer.reserved(("match_all"));

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) (bnfCom.parse(lexer));

        Assert.assertNotNull(program);

        AstObject obj = program.object();
        Assert.assertNotNull(obj);

        AstObjectProperty query = (AstObjectProperty) obj.property("query");
        Assert.assertNotNull(query);

        AstObject queryObj = query.object();
        Assert.assertNotNull(queryObj);

        AstObjectProperty matchAll = (AstObjectProperty) queryObj.property("match_all");
        Assert.assertNotNull(matchAll);
    }

    public static class FinderDemo3 {

        @Bind(name = "wrapper", mode = GrammarMode.WRAPPER)
        public void bind(AstQueryProgram property, Context context) {
            System.out.println(" run wrapper");
        }

        @Bind(name = "query", mode = GrammarMode.OBJECT, prefix = {"wrapper"})
        public void test(AstObjectProperty property, Context context) {
            System.out.println(" run query ");
        }

        @Bind(name = "bool", mode = GrammarMode.OBJECT, prefix = "query")
        public void bool(AstObjectProperty property, Context context) {
            System.out.println(" run bool ");
        }

//        @Bind(name = "must", mode = GrammarMode.OBJECT, prefix = {"bool"}, insert = "query")
//        public void must(AstObjectProperty property, Context context) {
//            System.out.println(" run must ");
//        }

        @Bind(name = "must", mode = GrammarMode.OBJECT, prefix = {"bool"}, insert = "query")
        public void must1(AstObjectProperty property, Context context) {
            System.out.println(" run must {} ");
        }
    }


    @Test
    public void testRecursion() {
        BnfGenerator generator = new BnfGenerator();
        generator.register(FinderDemo3.class);

        Assert.assertNotNull(generator);
        Assert.assertEquals(generator.getTokensBindMethods().size(), 4);


        JsonObject object = new JsonObject();
        JsonObject queryJsonObj = new JsonObject();
        JsonObject must = new JsonObject();

        JsonObject subQuery = new JsonObject();
        subQuery.add("bool", new JsonObject());

        must.add("must", new JsonObject());

//        JsonObject mustArray = new JsonObject();
//        mustArray.add("must", new JsonArray());
//
//        must.add("must", mustArray);
        queryJsonObj.add("bool", must);
        object.add("query", queryJsonObj);

        Logger.init();
        Logger.v(new GsonBuilder().setPrettyPrinting().create().toJson(object));

        Lexer lexer = new JustLexer(new GsonBuilder().setPrettyPrinting().create().toJson(object));
        lexer.reserved("query");
        lexer.reserved("bool");
        lexer.reserved("must");

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) (bnfCom.parse(lexer));
        Assert.assertNotNull(program);

        program.eval(generator.context());
    }
}