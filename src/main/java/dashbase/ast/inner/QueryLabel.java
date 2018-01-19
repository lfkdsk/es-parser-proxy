package dashbase.ast.inner;

import dashbase.ast.QueryAstList;
import dashbase.ast.base.AstNode;
import dashbase.token.Tokens;

import java.util.List;

public class QueryLabel extends QueryAstList{
    public QueryLabel(List<AstNode> children) {
        super(children, Tokens.QUERY);
    }

    public QueryAstList query() {
        return (QueryAstList) child(1);
    }
}
