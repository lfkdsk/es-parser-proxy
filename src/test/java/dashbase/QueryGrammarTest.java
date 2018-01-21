package dashbase;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.base.AstNode;
import dashbase.ast.object.AstObject;
import dashbase.ast.object.AstObjectProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.bnf.BnfCom;
import dashbase.lexer.JustLexer;
import dashbase.rules.QueryGrammar;
import dashbase.utils.GrammarHelper;
import dashbase.utils.json.JSONException;
import dashbase.utils.logger.Logger;
import org.junit.Assert;
import org.junit.Test;

import static dashbase.bnf.BnfCom.rule;
import static dashbase.utils.tools.TextUtils.w;

public class QueryGrammarTest {

    @Test
    public void testBaseGrammarSupport() throws JSONException {
        JsonObject problem = new JsonObject();
        problem.addProperty("0", true);
        problem.addProperty("1", 1000);
        problem.addProperty("2", 1000.01e10);
        problem.addProperty("3", "this is a string");
        problem.add("4", new JsonObject());
        problem.add("5", new JsonArray());

        JsonArray array6 = new JsonArray();
        array6.add(100);
        array6.add(100);
        array6.add(100);
        array6.add(100);

        problem.add("6", array6);


        JsonObject object7 = new JsonObject();
        object7.add("match_all", new JsonObject());
        object7.addProperty("lfkdsk", true);
        problem.add("7", object7);

        Logger.init();

        String unformatJsonString = problem.toString();
        Logger.v(unformatJsonString);

        ///////////////////////////////////////////////////////////////////////////
        // Use Lexer
        ///////////////////////////////////////////////////////////////////////////

        JustLexer lexer = new JustLexer(unformatJsonString);
        QueryGrammar grammar = new QueryGrammar();
        AstNode node = grammar.parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertEquals(node.child(0).child(0).childCount(), 8);

        System.out.println(" un-format accepted !");

        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(problem);
        Logger.v(jsonString);

        lexer = new JustLexer(jsonString);
        grammar = new QueryGrammar();
        node = grammar.parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertEquals(node.child(0).child(0).childCount(), 8);

        System.out.println(" formatted accepted !");
    }

    @Test
    public void testObjectProperty() {
        JsonObject problem = new JsonObject();
        problem.add("lfkdsk", new JsonObject());
        String str = new GsonBuilder().setPrettyPrinting().create().toJson(problem);
        AstQueryProgram program = TestUtils.runGrammar(str);
        Assert.assertNotNull(program);
    }


    @Test
    public void testWrapperObjectProperty() {

        ///////////////////////////////////////////////////////////////////////////
        // Special Auto-Generate Parser
        ///////////////////////////////////////////////////////////////////////////

        QueryGrammar grammar = new QueryGrammar();

        BnfCom wrapperPropertyList = rule(AstPropertyList.class)
                .option(rule(AstObjectProperty.class).literal(w("query")).sep(":").ast(grammar.getObject()).maybe(","))
                .option(rule(AstObjectProperty.class).literal(w("lfkdsk")).sep(":").ast(grammar.getObject()).maybe(","))
                .option(grammar.getProperty())
                .repeat(
                        rule().sep(",").repeat(grammar.getProperty())
                );

        BnfCom wrapperObject = rule(AstObject.class).sep("{")
                                                    .maybe(wrapperPropertyList)
                                                    .sep("}");


        JsonObject problem = new JsonObject();
        problem.add("query", new JsonObject());
        problem.add("lfkdsk", new JsonObject());
        problem.add("12", new JsonObject());

        JustLexer lexer = new JustLexer(problem.toString());
        lexer.reserved(w("query"));
        lexer.reserved(w("lfkdsk"));

        AstObject node = (AstObject) GrammarHelper.transformAst(wrapperObject.parse(lexer));
        Assert.assertNotNull(node);

        AstPropertyList list = node.propertyList();
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.childCount());

        AstObjectProperty obj1 = (AstObjectProperty) list.child(0);
        AstObjectProperty obj2 = (AstObjectProperty) list.child(1);

        Assert.assertEquals(obj1.keyNode().value(), w("query"));
        Assert.assertEquals(obj2.keyNode().value(), w("lfkdsk"));

    }
}