package dashbase.ast.property;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.literal.StringLiteral;

import java.util.List;

public abstract class Property extends QueryAstList {

    public Property(List<AstNode> children, int tag) {
        super(children, tag);
    }

    public StringLiteral keyNode() {
        return (StringLiteral) child(0);
    }

    public AstNode valueNode() {
        return child(1);
    }
}
