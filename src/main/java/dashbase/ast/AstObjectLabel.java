package dashbase.ast;

import com.lfkdsk.justel.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstObjectLabel extends QueryAstList {
    public AstObjectLabel(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT_LABEL);
    }

    public List<AstNode> labels() {
        return children.subList(1, childCount() - 1);
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
