package dashbase.ast.object;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.env.Context;
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
        if (child(0).childCount() == 0) {
            return new AstPropertyList(Collections.emptyList());
        }
        // empty list
        return (AstPropertyList) child(0);
    }

    public Property property(String name) {
        return propertyList().child(name);
    }

    @Override
    public void eval(Context context) {
        for (AstNode node : propertyList()) {
            node.eval(context);
        }
    }
}
