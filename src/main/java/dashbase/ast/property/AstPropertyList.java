package dashbase.ast.property;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.QueryAstList;
import dashbase.env.Context;
import dashbase.token.Tokens;

import java.util.HashMap;
import java.util.LinkedList;
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

        List<AstNode> resetList = new LinkedList<>(children);
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
                case Tokens.PROPERTY_LIST: {
                    resetList.remove(child);
                    AstPropertyList propertyList = (AstPropertyList) child;
                    for (Map.Entry<String, Property> stringPropertyEntry : propertyList.properties.entrySet()) {
                        properties.put(stringPropertyEntry.getKey(), stringPropertyEntry.getValue());
                        resetList.add(stringPropertyEntry.getValue());
                    }
                    break;
                }
            }
        }

        this.children = resetList;
    }

    public Property child(String name) {
        return properties.get(name);
    }

    @Override
    public void eval(Context context) {
        for (AstNode node : this) {
            ((QueryAstList) node).eval(context);
        }
    }
}
