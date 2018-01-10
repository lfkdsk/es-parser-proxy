package dashbase;

import com.google.gson.GsonBuilder;
import com.lfkdsk.justel.ast.base.AstNode;
import com.lfkdsk.justel.lexer.Lexer;
import com.lfkdsk.justel.token.Token;
import com.lfkdsk.justel.utils.json.JSONException;
import com.lfkdsk.justel.utils.json.JSONObject;
import com.lfkdsk.justel.utils.logger.Logger;
import dashbase.lexer.QueryLexer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

public class QueryGrammarTest {

    @Test
    public void testPrimary() {
        String[] args = {"\"lfkdsk\"", "true", "100000"};
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            Lexer lexer = new QueryLexer(arg);
            Queue<Token> tokens = lexer.tokens();
            AstNode node = new QueryGrammar().getPrimary().parse(tokens);

            if (i == 0) {
                Assert.assertEquals(node.child(0).getClass().getSimpleName(), "StringLiteral");
            } else if (i == 1) {
                Assert.assertEquals(node.child(0).getClass().getSimpleName(), "BoolLiteral");
            } else if (i == 2) {
                Assert.assertEquals(node.child(0).getClass().getSimpleName(), "NumberLiteral");
            }
        }
    }

    @Test
    public void testLabel() {
        String[] labels = {"{}", "[]", "\"\"", "true"};
        for (String label : labels) {
            Lexer lexer = new QueryLexer(label);
            Queue<Token> tokens = lexer.tokens();
            AstNode node = new QueryGrammar().getLabel().parse(tokens);
            Assert.assertEquals(node.getClass().getSimpleName(), "AstLabelExpr");
        }
    }

    @Test
    public void testMatchNone() {
        String matchNone = "{ \"query\": { \"match_none\": {} } }";
        Lexer lexer = new QueryLexer(matchNone);
        Queue<Token> tokens = lexer.tokens();
        AstNode node = new QueryGrammar().getProgram().parse(tokens);
        Assert.assertNotNull(node);
    }

    @Test
    public void testMatchAll() {
        String matchNone = " { \"query\": { \"match_all\": {} } }";
        Lexer lexer = new QueryLexer(matchNone);
        Queue<Token> tokens = lexer.tokens();
        AstNode node = new QueryGrammar().getProgram().parse(tokens);
        Logger.init();
        Logger.v(node.toString());
        Assert.assertNotNull(node);
    }

    @Test
    public void testMatch() {
        String matchNone = "{" +
                "    \"query\": {" +
                "        \"match\" : {" +
                "            \"message\" : {" +
                "                \"query\" : \"this is a test\"," +
                "                \"operator\" : \"and\"" +
                "            }" +
                "        }" +
                "    }" +
                "}";
        Lexer lexer = new QueryLexer(matchNone);
        Queue<Token> tokens = lexer.tokens();
        AstNode node = new QueryGrammar().getProgram().parse(tokens);
        Assert.assertNotNull(node);
    }
}