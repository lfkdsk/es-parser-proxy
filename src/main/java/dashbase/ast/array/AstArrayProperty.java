package dashbase.ast.array;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstArrayProperty extends QueryAstList{
    public AstArrayProperty(List<AstNode> children) {
        super(children, Tokens.AST_ARRAY_PROPERTY);
    }
}
