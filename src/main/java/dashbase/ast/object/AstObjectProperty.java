package dashbase.ast.object;

import dashbase.ast.base.AstNode;
import dashbase.ast.property.Property;
import dashbase.token.Tokens;

import java.util.List;

public class AstObjectProperty extends Property {
    public AstObjectProperty(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT_PROPERTY);
    }

    public AstObject object() {
        return (AstObject) child(1);
    }
}
