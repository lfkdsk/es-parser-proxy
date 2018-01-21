package dashbase.bnf.leaf;

import dashbase.ast.base.AstLeaf;
import dashbase.ast.base.AstNode;
import dashbase.bnf.base.Element;
import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;
import dashbase.token.Token;

import java.util.List;

/**
 * 叶节点
 */
public class Leaf extends Element {
    protected String[] tokens;

    public Leaf(String[] pat) {
        this.tokens = pat;
    }

    @Override
    public void parse(JustLexer lexer, List<AstNode> nodes) throws ParseException {
        Token token = lexer.read();

        if (token.isIdentifier()) {
            for (String t : tokens) {
                if (t.equals(token.getText())) {
                    find(nodes, token);
                    return;
                }
            }
        }

        if (tokens.length > 0) {
            throw new ParseException(tokens[0] + " expected. ", token);
        } else {
            throw new ParseException(token);
        }
    }

    /**
     * 添加终结符
     *
     * @param list  list
     * @param token 终结符对应token
     */
    protected void find(List<AstNode> list, Token token) {
        list.add(new AstLeaf(token));
    }

    @Override
    public boolean match(JustLexer lexer) throws ParseException {
        Token token = lexer.peek(0);

        if (token.isIdentifier()) {
            for (String t : tokens) {
                if (t.equals(token.getText())) {
                    return true;
                }
            }
        }

        return false;
    }
}
