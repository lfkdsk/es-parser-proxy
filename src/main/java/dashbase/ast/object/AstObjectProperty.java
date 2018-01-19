package dashbase.ast.object;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstObjectProperty extends QueryAstList {
    public AstObjectProperty(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT_PROPERTY);
    }
}
