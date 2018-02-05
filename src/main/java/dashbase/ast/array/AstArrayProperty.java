package dashbase.ast.array;

import bnfgenast.ast.base.AstNode;
import bnfgenast.ast.token.Token;
import dashbase.ast.property.Property;
import dashbase.ast.value.AstValueList;
import dashbase.ast.token.Tokens;

import java.util.List;

/**
 * array property := string: []
 *
 * @author liufengkai
 */
public class AstArrayProperty extends Property {

    public enum ArrayType {
        STRING, NUMBER, BOOL, OBJECT, ARRAY, EMPTY
    }

    public AstArrayProperty(List<AstNode> children) {
        super(children, Tokens.AST_ARRAY_PROPERTY);
    }

    public ArrayType arrayType() {
        if (list().childCount() == 0) {
            return ArrayType.EMPTY;
        }

        int tag = list().child(0).getTag();
        switch (tag) {
            case Token.STRING: {
                return ArrayType.STRING;
            }
            case Token.BOOLEAN: {
                return ArrayType.BOOL;
            }
            case Token.DOUBLE:
            case Token.LONG:
            case Token.FLOAT:
            case Token.INTEGER: {
                return ArrayType.NUMBER;
            }
            case Tokens.AST_OBJECT: {
                return ArrayType.OBJECT;
            }
            case Tokens.AST_ARRAY: {
                return ArrayType.ARRAY;
            }
        }

        return ArrayType.EMPTY;
    }

    public AstValueList list() {
        return (AstValueList) child(1);
    }


}
