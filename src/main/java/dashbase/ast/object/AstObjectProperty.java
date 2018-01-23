package dashbase.ast.object;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.property.Property;
import dashbase.env.Context;
import dashbase.token.Tokens;

import java.util.List;

public class AstObjectProperty extends Property {
    public AstObjectProperty(List<AstNode> children) {
        super(children, Tokens.AST_OBJECT_PROPERTY);
    }

    public AstObject object() {
        return (AstObject) child(1);
    }

    @Override
    public void eval(Context context) {
        object().eval(context);
        super.eval(context);
    }
}
