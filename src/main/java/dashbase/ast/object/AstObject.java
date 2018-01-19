package dashbase.ast.object;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.property.AstProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.ast.property.Property;
import dashbase.token.Tokens;

import java.util.List;

public class AstObject extends QueryAstList {
    public AstObject(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT);
    }

    public AstPropertyList propertyList() {
        return (AstPropertyList) child(0);
    }

    public AstProperty child(String name) {
        for (AstNode node : propertyList()) {
            AstProperty propertyParent = (AstProperty) node;
            Property property = propertyParent.property();

            if (property.keyNode().value().equals(name)) {
                return propertyParent;
            }
        }

        return null;
    }
}
