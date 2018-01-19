package dashbase.ast.array;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

/**
 * array := []
 *
 * @author liufengkai
 */
public class AstArray extends QueryAstList {
    public AstArray(List<AstNode> children) {
        super(children, Tokens.AST_ARRAY);
    }
}
