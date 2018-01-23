package dashbase.start;

import bnfgenast.ast.token.Token;
import bnfgenast.lexer.Lexer;
import dashbase.lexer.JustLexer;
import dashbase.utils.CodeDialog;
import logger.Logger;

import java.text.ParseException;

public class RunLexer {
    public static void main(String[] args) throws ParseException {
        Lexer lexer = new JustLexer(new CodeDialog());

        Logger.init("JustLexer");

        for (Token token; (token = lexer.read()) != Token.EOF; ) {
            Logger.i(" => " + token.getText() + " = " + token.getTag());
        }
    }
}
