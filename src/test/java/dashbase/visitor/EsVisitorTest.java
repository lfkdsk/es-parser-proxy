package dashbase.visitor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lfkdsk.justel.lexer.Lexer;
import com.lfkdsk.justel.token.Token;
import com.lfkdsk.justel.utils.logger.Logger;
import dashbase.QueryGrammar;
import dashbase.ast.AstQueryProgram;
import dashbase.lexer.QueryLexer;
import dashbase.request.Request;
import dashbase.request.Requests;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

public class EsVisitorTest {

    @Test
    public void testSingleStringQuery() {
        EsVisitor visitor = new EsVisitor();
        QueryGrammar grammar = new QueryGrammar();
        Lexer lexer = new QueryLexer("{" +
                "       \"query\": {" +
                "           \"match\" : {" +
                "               \"kind\" : \"SERVER\"" +
                "           }" +
                "       }" +
                "   }");
        Queue<Token> tokens = lexer.tokens();
        AstQueryProgram queryProgram = grammar.getAst(tokens);
        Request request = visitor.visitAstQueryProgram(queryProgram);
        Requests.queryAllFields(request);
        Logger.init();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Logger.v(gson.toJson(request));
    }
}