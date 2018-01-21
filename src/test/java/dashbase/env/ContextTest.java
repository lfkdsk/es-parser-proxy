package dashbase.env;

import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.bnf.BnfCom;
import dashbase.lexer.JustLexer;
import dashbase.meta.Bind;
import dashbase.meta.BnfGenerator;
import dashbase.meta.GrammarMode;
import dashbase.utils.GrammarHelper;
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

        JustLexer lexer = new JustLexer(object.toString());
        lexer.reserved(("query"));
        lexer.reserved(("match_all"));

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) GrammarHelper.transformAst(bnfCom.parse(lexer));

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
}