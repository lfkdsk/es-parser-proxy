package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstLabelExpr extends QueryAstList{
    public AstLabelExpr(List<AstNode> children) {
        super(children, Tokens.AST_PRIMARY_LABEL);
    }

    @Override
    public String toString() {
        return child(0).toString();
    }
}
