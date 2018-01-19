package dashbase.ast.array;

import dashbase.ast.base.AstNode;
import dashbase.ast.property.Property;
import dashbase.ast.value.AstValueList;
import dashbase.token.Tokens;

import java.util.List;

/**
 * array property := string: []
 *
 * @author liufengkai
 */
public class AstArrayProperty extends Property {
    public AstArrayProperty(List<AstNode> children) {
        super(children, Tokens.AST_ARRAY_PROPERTY);
    }

    public AstValueList list() {
        return (AstValueList) child(1);
    }
}
