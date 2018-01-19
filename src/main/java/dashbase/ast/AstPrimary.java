package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstPrimary extends QueryAstList {
    public AstPrimary(List<AstNode> children) {
        super(children, Tokens.AST_PRIMARY);
    }

    public String text() {
        return child(0).toString() + ":" + child(1).toString();
    }
}
