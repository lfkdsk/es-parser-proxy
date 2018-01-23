package dashbase.ast.value;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.ast.literal.Literal;
import dashbase.ast.object.AstObject;
import dashbase.token.Tokens;

import java.util.List;
import java.util.stream.Collectors;

/**
 * "lfkdsk": [
 * <p>
 * ]
 * [] is literal-list
 * literal-list := [primary | object | array]
 */
public class AstValueList extends QueryAstList {
    public AstValueList(List<AstNode> children) {
        super(children, Tokens.VALUE_LIST);
    }

    @SuppressWarnings("unchecked")
    public <T extends Literal> List<T> toLiterals() {
        return children.stream()
                       .map(node -> (T) node)
                       .collect(Collectors.toList());
    }

    public List<AstObject> toObjects() {
        return children.stream()
                       .map(node -> (AstObject) node)
                       .collect(Collectors.toList());
    }

    public List<AstValueList> toArrays() {
        return children.stream()
                       .map(node -> (AstValueList) node)
                       .collect(Collectors.toList());
    }
}
