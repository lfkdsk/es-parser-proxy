package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.ast.object.AstObject;
import dashbase.token.Tokens;

import java.util.List;

public class AstQueryProgram extends QueryAstList {

    public AstQueryProgram(List<AstNode> children) {
        super(children, Tokens.QUERY_PROBLEM);
    }

    public AstObject object() {
        // starter is an object-label
        return (AstObject) this.child(0);
    }

}
