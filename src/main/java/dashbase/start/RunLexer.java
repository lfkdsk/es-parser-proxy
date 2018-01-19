package dashbase.start;

import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;
import dashbase.token.Token;
import dashbase.utils.CodeDialog;
import dashbase.utils.logger.Logger;

public class RunLexer {
    public static void main(String[] args) throws ParseException {
        JustLexer lexer = new JustLexer(new CodeDialog());

        Logger.init("JustLexer");

        for (Token token; (token = lexer.read()) != Token.EOF; ) {
            Logger.i(" => " + token.getText() + " = " + token.getTag());
        }
    }
}
