package dashbase.ast.object;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.property.AstProperty;
import dashbase.ast.property.AstPropertyList;
import dashbase.token.Tokens;

import java.util.List;

public class AstObject extends QueryAstList {
    public AstObject(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT);
    }

    public AstPropertyList propertyList() {
        return (AstPropertyList) child(0);
    }

    public AstProperty property(String name) {
        return propertyList().child(name);
    }
}
