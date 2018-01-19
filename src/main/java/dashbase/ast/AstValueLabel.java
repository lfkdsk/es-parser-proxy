package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstValueLabel extends QueryAstList{

    public AstValueLabel(List<AstNode> children) {
        super(children, Tokens.AST_VALUE_LABEL);
    }

    @Override
    public String toString() {
        return child(0).toString();
    }
}
