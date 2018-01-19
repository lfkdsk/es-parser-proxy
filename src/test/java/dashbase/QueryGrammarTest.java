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

    //    @Test
//    public void testPrimary() {
//        String[] args = {"\"lfkdsk\"", "true", "100000"};
//        for (int i = 0; i < args.length; i++) {
//            String arg = args[i];
//            JustLexer lexer = new JustLexer(arg);
//            Queue<Token> tokens = lexer.tokens();
//            AstNode node = new QueryGrammar().getPrimary().parse(tokens);
//
//            if (i == 0) {
//                Assert.assertEquals(node.child(0).getClass().getSimpleName(), "StringLiteral");
//            } else if (i == 1) {
//                Assert.assertEquals(node.child(0).getClass().getSimpleName(), "BoolLiteral");
//            } else if (i == 2) {
//                Assert.assertEquals(node.child(0).getClass().getSimpleName(), "NumberLiteral");
//            }
//        }
//    }
//
//    @Test
//    public void testLabel() {
//        String[] labels = {"{}", "[]", "\"\"", "true"};
//        for (String label : labels) {
//            Lexer lexer = new QueryLexer(label);
//            Queue<Token> tokens = lexer.tokens();
//            AstNode node = new QueryGrammar().getLabel().parse(tokens);
//            Assert.assertEquals(node.getClass().getSimpleName(), "AstLabelExpr");
//        }
//    }
//
//    @Test
//    public void testMatchNone() {
//        String matchNone = "{ \"query\": { \"match_none\": {} } }";
//        Lexer lexer = new QueryLexer(matchNone);
//        Queue<Token> tokens = lexer.tokens();
//        AstNode node = new QueryGrammar().getProgram().parse(tokens);
//        Assert.assertNotNull(node);
//    }
//
//    @Test
//    public void testMatchAll() {
//        String matchNone = " { \"query\": { \"match_all\": {} } }";
//        Lexer lexer = new QueryLexer(matchNone);
//        Queue<Token> tokens = lexer.tokens();
//        AstNode node = new QueryGrammar().getProgram().parse(tokens);
//        Logger.init();
//        Logger.v(node.toString());
//        Assert.assertNotNull(node);
//    }
//
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
        problem.add("7", object7);

        Logger.init();

        String unformatJsonString = problem.toString();
        Logger.v(unformatJsonString);

        ///////////////////////////////////////////////////////////////////////////
        // Use Lexer
        ///////////////////////////////////////////////////////////////////////////

        JustLexer lexer = new JustLexer(unformatJsonString);
        QueryGrammar grammar = new QueryGrammar();
        AstNode node = grammar.getProgram().parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstQueryProgram);
        Assert.assertEquals(node.child(0).child(0).childCount(), 8);

        System.out.println(" un-format accepted !");

        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(problem);
        Logger.v(jsonString);

        lexer = new JustLexer(jsonString);
        grammar = new QueryGrammar();
        node = grammar.getProgram().parse(lexer);

        Assert.assertNotNull(node);
        Assert.assertTrue(node instanceof AstQueryProgram);
        Assert.assertEquals(node.child(0).child(0).childCount(), 8);

        System.out.println(" formatted accepted !");
    }
}