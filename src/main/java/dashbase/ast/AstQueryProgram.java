package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstQueryProgram extends QueryAstList {

    public AstQueryProgram(List<AstNode> children) {
        super(children, Tokens.QUERY_PROBLEM);
    }

    public QueryAstList program() {
        // starter is an object-label
        return (QueryAstList) this.child(0).child(0);
    }

}
