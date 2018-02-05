package dashbase.ast;

import bnfgenast.ast.base.AstNode;
import dashbase.ast.object.AstObject;
import dashbase.env.Context;
import dashbase.ast.token.Tokens;

import java.util.List;

public class AstQueryProgram extends QueryAstList {

    public AstQueryProgram(List<AstNode> children) {
        super(children, Tokens.QUERY_PROBLEM);
    }

    public AstObject object() {
        // starter is an object-label
        return (AstObject) this.child(0);
    }

    @Override
    public void eval(Context context) {
        object().eval(context);
    }
}
