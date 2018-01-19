package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstArrayLabel extends QueryAstList {
    public AstArrayLabel(List<AstNode> children) {
        super(children, Tokens.AST_ARRAY_LABEL);
    }
}
