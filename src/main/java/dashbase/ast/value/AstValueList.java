package dashbase.ast.value;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

/**
 * "lfkdsk": [
 * <p>
 * ]
 * [] is literal-list
 */
public class AstValueList extends QueryAstList {
    public AstValueList(List<AstNode> children) {
        super(children, Tokens.VALUE_LIST);
    }

    public AstValue get(int index) {
        return (AstValue) child(index);
    }
}
