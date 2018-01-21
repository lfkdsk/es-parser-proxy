package dashbase.env;

import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.object.AstObjectProperty;
import dashbase.bnf.BnfCom;
import dashbase.lexer.JustLexer;
import dashbase.meta.Bind;
import dashbase.meta.BnfGenerator;
import dashbase.meta.GrammarMode;
import dashbase.utils.GrammarHelper;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.utils.tools.TextUtils.w;

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
        JsonObject query = new JsonObject();

        query.add("match_all", new JsonObject());
        object.add("query", query);

        JustLexer lexer = new JustLexer(object.toString());
        lexer.reserved(w("query"));
        lexer.reserved(w("match_all"));

        BnfCom bnfCom = generator.generate();
        AstQueryProgram program = (AstQueryProgram) GrammarHelper.transformAst(bnfCom.parse(lexer));

        Assert.assertNotNull(program);

    }
}