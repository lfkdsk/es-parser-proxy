package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstLabelList extends QueryAstList {
    public AstLabelList(List<AstNode> children) {
        super(children, Tokens.OBJECT_LIST);
    }

    public List<AstNode> labels() {
        return children;
    }
}
