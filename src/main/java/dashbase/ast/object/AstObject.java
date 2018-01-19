package dashbase.ast.object;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstObject extends QueryAstList {
    public AstObject(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT);
    }

    public List<AstNode> labels() {
        return children;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (AstNode node : labels()) {
            builder.append(node.toString());
        }

        return builder.toString();
    }
}
