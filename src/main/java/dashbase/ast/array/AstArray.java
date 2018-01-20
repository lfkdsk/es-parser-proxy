package dashbase.ast.array;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.value.AstValueList;
import dashbase.token.Tokens;

import java.util.Collections;
import java.util.List;

/**
 * array := []
 * 中间层 不会出现在 AST 结构之中
 *
 * @author liufengkai
 */
public class AstArray extends QueryAstList {
    public AstArray(List<AstNode> children) {
        super(children, Tokens.AST_ARRAY);
    }

    public AstValueList list() {
        if (child(0).childCount() == 0) {
            return new AstValueList(Collections.emptyList());
        }

        return (AstValueList) child(0);
    }
}
