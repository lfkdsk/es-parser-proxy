package dashbase.ast.primary;

import dashbase.ast.base.AstNode;
import dashbase.ast.property.Property;
import dashbase.token.Tokens;

import java.util.List;

public class AstPrimaryProperty extends Property {
    public AstPrimaryProperty(List<AstNode> children) {
        super(children, Tokens.AST_PRIMARY_PROPERTY);
    }
}
