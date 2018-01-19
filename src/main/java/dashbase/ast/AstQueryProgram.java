package dashbase.ast;

import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class AstQueryProgram extends QueryAstList {

    public AstQueryProgram(List<AstNode> children) {
        super(children, Tokens.QUERY_PROBLEM);
    }

    public QueryAstList program() {
        return (QueryAstList) this.child(0);
    }

}
