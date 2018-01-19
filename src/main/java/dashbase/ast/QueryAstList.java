package dashbase.ast;

import dashbase.ast.base.AstList;
import dashbase.ast.base.AstNode;

import java.util.List;

public class QueryAstList extends AstList {


    public QueryAstList(List<AstNode> children, int tag) {
        super(children, tag);
    }

    @Override
    public String toString() {
        if (children.isEmpty()) {
            return "(empty)";
        }

        StringBuilder builder = new StringBuilder();

        for (AstNode child : children) {
            builder.append(child.toString());
        }

        return builder.toString();
    }
}
