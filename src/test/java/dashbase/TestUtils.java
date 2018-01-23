package dashbase;

import bnfgenast.lexer.Lexer;
import dashbase.ast.AstQueryProgram;
import dashbase.lexer.JustLexer;
import dashbase.rules.QueryGrammar;

public class TestUtils {
    public static Lexer createJustLexer(String expr) {
        return new JustLexer(expr);
    }

    public static QueryGrammar createGrammar() {
        return new QueryGrammar();
    }

    public static AstQueryProgram runGrammar(String expr) {
        Lexer lexer = createJustLexer(expr);
        QueryGrammar grammar = createGrammar();

        return grammar.parse(lexer);
    }


}
