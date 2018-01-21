package dashbase.bnf.leaf;

import dashbase.ast.base.AstNode;
import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;
import dashbase.token.Token;

import java.util.List;

public class SkipOR extends Leaf {

    public SkipOR(String pat) {
        super(new String[]{pat});
    }

    @Override
    protected void find(List<AstNode> list, Token token) {

    }

    @Override
    public void parse(JustLexer lexer, List<AstNode> nodes) throws ParseException {
        String pat = tokens[0];
        Token token = lexer.peek(0);

        if (token.getText().equals(pat)) {
            lexer.read();
        }
    }

    @Override
    public boolean match(JustLexer lexer) throws ParseException {
        return true;
    }
}
