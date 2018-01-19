package dashbase;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dashbase.ast.AstQueryProgram;
import dashbase.ast.base.AstNode;
import dashbase.lexer.JustLexer;
import dashbase.rules.QueryGrammar;
import dashbase.utils.json.JSONException;
import dashbase.utils.logger.Logger;
import org.junit.Assert;
import org.junit.Test;

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
        Logger.init();
        Logger.v(str);
        AstQueryProgram program = TestUtils.runGrammar(str);
    }
}