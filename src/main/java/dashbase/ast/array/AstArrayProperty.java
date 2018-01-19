package dashbase.ast.array;

import dashbase.ast.base.AstNode;
import dashbase.ast.property.Property;
import dashbase.token.Tokens;

import java.util.List;

public class AstArrayProperty extends Property {
    public AstArrayProperty(List<AstNode> children) {
        super(children, Tokens.AST_ARRAY_PROPERTY);
    }

    public Object value() {
        return null;
    }
}
