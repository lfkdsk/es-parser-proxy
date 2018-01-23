package dashbase.ast.primary;

import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import dashbase.ast.literal.Literal;
import dashbase.ast.property.Property;
import dashbase.token.Tokens;

import java.util.List;

public class AstPrimaryProperty extends Property {

    public enum PrimaryType {
        STRING, BOOL, NUMBER
    }

    public AstPrimaryProperty(List<AstNode> children) {
        super(children, Tokens.AST_PRIMARY_PROPERTY);
    }

    public Literal literal() {
        return (Literal) child(1);
    }

    public PrimaryType primaryType() {
        int tag = literal().token().getTag();
        switch (tag) {
            case Token.STRING: {
                return PrimaryType.STRING;
            }
            case Token.BOOLEAN: {
                return PrimaryType.BOOL;
            }
            case Token.DOUBLE:
            case Token.LONG:
            case Token.FLOAT:
            case Token.INTEGER: {
                return PrimaryType.NUMBER;
            }
        }

        throw new UnsupportedOperationException(" un supported primary type " + literal());
    }
}
