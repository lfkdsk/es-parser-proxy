package dashbase.ast.property;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.ast.env.Context;
import dashbase.token.Tokens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use is { } object
 * {
 * property - list
 * }
 * property list := [ property ]
 *
 * @author liufengkai
 */
public class AstPropertyList extends QueryAstList {
    private Map<String, Property> properties = new HashMap<>();

    public AstPropertyList(List<AstNode> children) {
        super(children, Tokens.PROPERTY_LIST);

        // after transformer
        for (AstNode child : children) {
            int tag = child.getTag();
            switch (tag) {
                case Tokens.PROPERTY: {
                    Property property = ((AstProperty) child).property();
                    properties.put(property.keyNode().value(), property);
                    break;
                }
                case Tokens.AST_ARRAY_PROPERTY:
                case Tokens.AST_OBJECT_PROPERTY:
                case Tokens.AST_PRIMARY_PROPERTY: {
                    Property property = (Property) child;
                    properties.put(property.keyNode().value(), property);
                    break;
                }
            }
        }
    }

    public Property child(String name) {
        return properties.get(name);
    }

    @Override
    public void eval(Context context) {
        for (AstNode node : this) {
            node.eval(context);
        }
    }
}
