package dashbase.ast.object;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.property.AstPropertyList;
import dashbase.ast.property.Property;
import dashbase.token.Tokens;

import java.util.Collections;
import java.util.List;

public class AstObject extends QueryAstList {
    public AstObject(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT);
    }

    public AstPropertyList propertyList() {
        if (child(0) instanceof AstPropertyList) {
            return (AstPropertyList) child(0);
        }
        // empty list

        return new AstPropertyList(Collections.emptyList());
    }

    public Property property(String name) {
        return propertyList().child(name);
    }
}
