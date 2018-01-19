package dashbase.ast.property;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.literal.StringLiteral;
import dashbase.token.Tokens;

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

    public AstProperty.PropertyType type() {
        int tag = this.getTag();

        switch (tag) {
            case Tokens.AST_PRIMARY_PROPERTY: {
                return AstProperty.PropertyType.PRIMARY;
            }
            case Tokens.AST_ARRAY_PROPERTY: {
                return AstProperty.PropertyType.ARRAY;
            }
            default:
            case Tokens.AST_OBJECT_PROPERTY: {
                return AstProperty.PropertyType.OBJECT;
            }
        }
    }
}
