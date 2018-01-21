package dashbase.bnf.token;

import dashbase.ast.literal.StringLiteral;
import dashbase.token.Token;

public class StableStringToken extends AToken {

    private String value;

    public StableStringToken(String value) {
        super(StringLiteral.class);
        this.value = value;
    }

    @Override
    protected boolean tokenTest(Token token) {
        return token.getText().equals(value);
    }
}
