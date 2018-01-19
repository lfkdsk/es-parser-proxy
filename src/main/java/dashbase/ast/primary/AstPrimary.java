package dashbase.ast.primary;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.literal.BoolLiteral;
import dashbase.ast.literal.Literal;
import dashbase.ast.literal.NumberLiteral;
import dashbase.ast.literal.StringLiteral;
import dashbase.token.Tokens;

import java.util.List;

/**
 * AstPrimary 指的就是具体的值类型
 * 支持的值类型有 string | bool | number (int/float/long/double)
 *
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
        return literal().name();
    }

    public PrimaryType type() {
        Literal value = literal();
        if (value instanceof BoolLiteral) {
            return PrimaryType.BOOL;
        } else if (value instanceof StringLiteral) {
            return PrimaryType.STRING;
        } else if (value instanceof NumberLiteral) {
            return PrimaryType.NUMBER;
        }

        throw new UnsupportedOperationException("UnSupported value type " + value.toString());
    }
}
