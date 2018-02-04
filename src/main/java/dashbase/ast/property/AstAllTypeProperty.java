package dashbase.ast.property;

import bnfgenast.ast.base.AstNode;
import dashbase.env.Context;
import dashbase.token.Tokens;

import java.util.List;

public class AstAllTypeProperty extends Property {
    public AstAllTypeProperty(List<AstNode> children) {
        super(children, Tokens.AST_ALL_PROPERTY);
    }

    @Override
    public void eval(Context context) {
        super.eval(context);
    }
}
