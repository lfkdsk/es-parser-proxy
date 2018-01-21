package dashbase.bnf.token;

import dashbase.ast.base.AstLeaf;
import dashbase.ast.base.AstNode;
import dashbase.bnf.base.Element;
import dashbase.bnf.base.Factory;
import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;
import dashbase.token.Token;

import java.util.List;

/**
 * Token 基类
 */
public abstract class AToken extends Element {

    protected Factory factory;

    public AToken(Class<? extends AstLeaf> clazz) {
        if (clazz == null) {
            clazz = AstLeaf.class;
        }

        factory = Factory.get(clazz, Token.class);
    }

    @Override
    public boolean match(JustLexer lexer) throws ParseException {
        return tokenTest(lexer.peek(0));
    }

    @Override
    public void parse(JustLexer lexer, List<AstNode> nodes) throws ParseException {
        Token token = lexer.read();

        if (tokenTest(token)) {
            AstNode leaf = factory.make(token);

            nodes.add(leaf);
        } else {
            throw new ParseException(token);
        }
    }

    /**
     * 判断是否符合该类Token
     * 标准的抽象方法
     *
     * @param token token
     * @return tof?
     */
    protected abstract boolean tokenTest(Token token);
}
