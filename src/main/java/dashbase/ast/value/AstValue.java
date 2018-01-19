package dashbase.ast.value;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstValue extends QueryAstList {
    public AstValue(List<AstNode> children) {
        super(children, Tokens.AST_VALUE);
    }

    @Override
    public String toString() {
        return child(0).toString();
    }
}
