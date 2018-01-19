package dashbase.ast.primary;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.literal.Literal;
import dashbase.token.Token;
import dashbase.token.Tokens;

import java.util.List;

/**
 * AstPrimary 指的就是具体的值类型
 * 支持的值类型有 string | bool | number (int/float/long/double)
 * @author liufengkai
 */
public class AstPrimary extends QueryAstList {

    public enum PrimaryType {
        STRING, BOOL, NUMBER
    }

    public AstPrimary(List<AstNode> children) {
        super(children, Tokens.AST_PRIMARY);
    }

    public Literal literal() {
        return (Literal) child(0);
    }

    public Object value() {
        return literal().value();
    }

    public PrimaryType type() {
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

        throw new UnsupportedOperationException(" un supported primary type " + value());
    }
}
