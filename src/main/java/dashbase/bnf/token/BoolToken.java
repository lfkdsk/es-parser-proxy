package dashbase.bnf.token;

import dashbase.ast.base.AstLeaf;
import dashbase.token.Token;

public class BoolToken extends AToken {

    public BoolToken(Class<? extends AstLeaf> clazz) {
        super(clazz);
    }

    @Override
    public boolean tokenTest(Token token) {
        return token.isBool();
    }
}
